/**
 * Google Sheets Sync Service
 *
 * Synchronizes Epic/Feature data between Google Sheets and the local FeatureRegistry.
 *
 * Commands:
 *   node scripts/sync-google-sheets.js fetch     - Fetch and display data
 *   node scripts/sync-google-sheets.js generate  - Generate FeatureRegistry.js
 *   node scripts/sync-google-sheets.js stats     - Show coverage statistics
 *
 * Configuration:
 *   - Spreadsheet ID: 1HYDaiNZVseCg4WtIaxJQ-LLclNl7vkHXp6WsMEaKK9A
 *   - Epic sheet: "Epic" (columns: No, Epic, Product Type, Tag)
 *   - Feature sheet: "Feature" (columns: No, Feature, Feature DE, Epic, Impact)
 */

const { google } = require('googleapis');
const fs = require('fs');
const path = require('path');

// Configuration
const SPREADSHEET_ID = '1HYDaiNZVseCg4WtIaxJQ-LLclNl7vkHXp6WsMEaKK9A';
const CREDENTIALS_PATH = path.join(__dirname, '../credentials/google-sheets-service-account.json');
const OUTPUT_PATH = path.join(__dirname, '../tests/utils/FeatureRegistry.generated.js');

// Google Sheets link template
const SHEET_LINK_BASE = `https://docs.google.com/spreadsheets/d/${SPREADSHEET_ID}/edit`;

class GoogleSheetsSyncService {
  constructor() {
    this.auth = null;
    this.sheets = null;
  }

  async initialize() {
    if (!fs.existsSync(CREDENTIALS_PATH)) {
      throw new Error(`Credentials file not found at: ${CREDENTIALS_PATH}`);
    }

    const credentials = JSON.parse(fs.readFileSync(CREDENTIALS_PATH, 'utf8'));

    this.auth = new google.auth.GoogleAuth({
      credentials,
      scopes: ['https://www.googleapis.com/auth/spreadsheets.readonly'],
    });

    this.sheets = google.sheets({ version: 'v4', auth: this.auth });
  }

  /**
   * Fetch all epics from the Epic sheet.
   * Only includes epics with Product Type = "Software".
   * @returns {Array<{id: string, name: string, productType: string, tag: string}>}
   */
  async fetchEpics() {
    const response = await this.sheets.spreadsheets.values.get({
      spreadsheetId: SPREADSHEET_ID,
      range: 'Epic!A2:E1000',
    });

    const rows = response.data.values || [];
    return rows
      .filter(row => row[0] && row[1]) // Must have ID and name
      .map((row, index) => ({
        id: row[0]?.trim() || '',
        name: row[1]?.trim() || '',
        productType: row[2]?.trim() || '',
        tag: row[3]?.trim() || '',
        rowNumber: index + 2, // For linking to specific row
      }))
      .filter(epic => epic.productType === 'Software'); // Only Software epics
  }

  /**
   * Fetch all features from the Feature sheet.
   * Only includes features that belong to Software epics.
   * @param {Set<string>} softwareEpicNames - Set of epic names with Product Type = "Software"
   * @returns {Array<{id: string, name: string, nameDE: string, epicName: string, impact: string}>}
   */
  async fetchFeatures(softwareEpicNames) {
    const response = await this.sheets.spreadsheets.values.get({
      spreadsheetId: SPREADSHEET_ID,
      range: 'Feature!A2:F1000',
    });

    const rows = response.data.values || [];
    return rows
      .filter(row => row[0] && row[1]) // Must have ID and name
      .map((row, index) => ({
        id: row[0]?.trim() || '',
        name: row[1]?.trim() || '',
        nameDE: row[2]?.trim() || '',
        epicName: row[3]?.trim() || '',
        impact: row[4]?.trim() || '',
        rowNumber: index + 2, // For linking to specific row
      }))
      .filter(feature => !softwareEpicNames || softwareEpicNames.has(feature.epicName)); // Only features for Software epics
  }

  /**
   * Get link to a specific Epic row in Google Sheets.
   */
  getEpicLink(epic) {
    return `${SHEET_LINK_BASE}#gid=0&range=A${epic.rowNumber}`;
  }

  /**
   * Get link to a specific Feature row in Google Sheets.
   */
  getFeatureLink(feature) {
    return `${SHEET_LINK_BASE}#gid=1284833774&range=A${feature.rowNumber}`;
  }

  /**
   * Generate FeatureRegistry.js from Google Sheets data.
   */
  async generateFeatureRegistry() {
    console.log('Fetching data from Google Sheets...');
    const epics = await this.fetchEpics();

    // Create set of Software epic names to filter features
    const softwareEpicNames = new Set(epics.map(e => e.name));
    const features = await this.fetchFeatures(softwareEpicNames);

    console.log(`Found ${epics.length} epics and ${features.length} features`);

    // Build epic lookup by name (since features reference by name, not ID)
    const epicByName = {};
    epics.forEach(epic => {
      epicByName[epic.name] = epic;
    });

    // Group features by epic
    const featuresByEpic = {};
    features.forEach(feature => {
      const epicName = feature.epicName;
      if (!featuresByEpic[epicName]) {
        featuresByEpic[epicName] = [];
      }
      featuresByEpic[epicName].push(feature);
    });

    // Generate JavaScript code (CommonJS format for compatibility)
    const timestamp = new Date().toISOString();
    let code = `/**
 * Feature Registry - Auto-generated from Google Sheets
 *
 * DO NOT EDIT MANUALLY - Changes will be overwritten!
 *
 * Generated: ${timestamp}
 * Source: ${SHEET_LINK_BASE}
 *
 * To regenerate: node scripts/sync-google-sheets.js generate
 */

// Google Sheets configuration
const SPREADSHEET_ID = '${SPREADSHEET_ID}';
const SHEET_LINK_BASE = '${SHEET_LINK_BASE}';

/**
 * Epic definitions from Google Sheets.
 * Key: Epic name (for lookup), Value: Epic details
 */
const Epics = {
`;

    // Add epics
    epics.forEach(epic => {
      const key = this.toSafeKey(epic.name);
      code += `  '${key}': {\n`;
      code += `    id: '${epic.id}',\n`;
      code += `    name: '${this.escapeString(epic.name)}',\n`;
      code += `    productType: '${this.escapeString(epic.productType)}',\n`;
      code += `    rowNumber: ${epic.rowNumber},\n`;
      code += `    link: '${this.getEpicLink(epic)}',\n`;
      code += `  },\n`;
    });

    code += `};

/**
 * Feature definitions from Google Sheets.
 * Key: Feature ID (e.g., 'F00100'), Value: Feature details
 */
const Features = {
`;

    // Add features
    features.forEach(feature => {
      const epic = epicByName[feature.epicName];
      code += `  '${feature.id}': {\n`;
      code += `    id: '${feature.id}',\n`;
      code += `    name: '${this.escapeString(feature.name)}',\n`;
      if (feature.nameDE) {
        code += `    nameDE: '${this.escapeString(feature.nameDE)}',\n`;
      }
      code += `    epicName: '${this.escapeString(feature.epicName)}',\n`;
      code += `    epicId: '${epic?.id || ''}',\n`;
      if (feature.impact) {
        code += `    impact: '${this.escapeString(feature.impact)}',\n`;
      }
      code += `    rowNumber: ${feature.rowNumber},\n`;
      code += `    link: '${this.getFeatureLink(feature)}',\n`;
      code += `  },\n`;
    });

    code += `};

/**
 * Get Epic by name.
 * @param {string} name - Epic name (e.g., 'Sales', 'Picking')
 * @returns {Object|undefined} Epic details
 */
function getEpic(name) {
  return Epics[name];
}

/**
 * Get Feature by ID.
 * @param {string} id - Feature ID (e.g., 'F00100')
 * @returns {Object|undefined} Feature details
 */
function getFeature(id) {
  return Features[id];
}

/**
 * Get all features for an epic.
 * @param {string} epicName - Epic name (e.g., 'Sales')
 * @returns {Array<Object>} Features belonging to this epic
 */
function getFeaturesByEpic(epicName) {
  return Object.values(Features).filter(f => f.epicName === epicName);
}

/**
 * Search features by name (partial match, case-insensitive).
 * @param {string} query - Search query
 * @returns {Array<Object>} Matching features
 */
function searchFeatures(query) {
  const lowerQuery = query.toLowerCase();
  return Object.values(Features).filter(f =>
    f.name.toLowerCase().includes(lowerQuery) ||
    (f.nameDE && f.nameDE.toLowerCase().includes(lowerQuery))
  );
}

/**
 * Get all epic names.
 * @returns {Array<string>} List of epic names
 */
function getAllEpicNames() {
  return Object.keys(Epics);
}

/**
 * Get statistics about the feature registry.
 * @returns {Object} Statistics
 */
function getStats() {
  const epicCount = Object.keys(Epics).length;
  const featureCount = Object.keys(Features).length;
  const featuresPerEpic = {};

  Object.values(Features).forEach(f => {
    featuresPerEpic[f.epicName] = (featuresPerEpic[f.epicName] || 0) + 1;
  });

  return {
    epicCount,
    featureCount,
    featuresPerEpic,
    generatedAt: '${timestamp}',
  };
}

// CommonJS exports
module.exports = {
  SPREADSHEET_ID,
  SHEET_LINK_BASE,
  Epics,
  Features,
  getEpic,
  getFeature,
  getFeaturesByEpic,
  searchFeatures,
  getAllEpicNames,
  getStats,
};
`;

    // Write to file
    fs.writeFileSync(OUTPUT_PATH, code, 'utf8');
    console.log(`\nGenerated: ${OUTPUT_PATH}`);
    console.log(`  - ${epics.length} epics`);
    console.log(`  - ${features.length} features`);

    return { epics, features };
  }

  /**
   * Display fetch results.
   */
  async displayFetchResults() {
    const epics = await this.fetchEpics();
    const softwareEpicNames = new Set(epics.map(e => e.name));
    const features = await this.fetchFeatures(softwareEpicNames);

    console.log('\n=== EPICS ===');
    console.log(`Total: ${epics.length}\n`);
    epics.slice(0, 10).forEach(epic => {
      console.log(`  ${epic.id}: ${epic.name} (${epic.productType})`);
    });
    if (epics.length > 10) {
      console.log(`  ... and ${epics.length - 10} more`);
    }

    console.log('\n=== FEATURES ===');
    console.log(`Total: ${features.length}\n`);
    features.slice(0, 15).forEach(feature => {
      console.log(`  ${feature.id}: ${feature.name}`);
      console.log(`           Epic: ${feature.epicName}`);
    });
    if (features.length > 15) {
      console.log(`  ... and ${features.length - 15} more`);
    }

    // Show features per epic
    console.log('\n=== FEATURES PER EPIC ===');
    const featuresPerEpic = {};
    features.forEach(f => {
      featuresPerEpic[f.epicName] = (featuresPerEpic[f.epicName] || 0) + 1;
    });

    Object.entries(featuresPerEpic)
      .sort((a, b) => b[1] - a[1])
      .slice(0, 15)
      .forEach(([epicName, count]) => {
        console.log(`  ${epicName}: ${count} features`);
      });
  }

  /**
   * Convert a name to a safe JavaScript key.
   */
  toSafeKey(name) {
    return name; // Keep as-is since we're using string keys in quotes
  }

  /**
   * Escape a string for JavaScript.
   */
  escapeString(str) {
    return str.replace(/\\/g, '\\\\').replace(/'/g, "\\'").replace(/\n/g, '\\n');
  }
}

// CLI
async function main() {
  const command = process.argv[2] || 'help';

  const service = new GoogleSheetsSyncService();

  try {
    await service.initialize();

    switch (command) {
      case 'fetch':
        await service.displayFetchResults();
        break;

      case 'generate':
        await service.generateFeatureRegistry();
        break;

      case 'stats':
        const statsEpics = await service.fetchEpics();
        const statsSoftwareEpicNames = new Set(statsEpics.map(e => e.name));
        const statsFeatures = await service.fetchFeatures(statsSoftwareEpicNames);
        console.log('\n=== STATISTICS (Software only) ===');
        console.log(`Epics: ${statsEpics.length}`);
        console.log(`Features: ${statsFeatures.length}`);
        console.log(`Average features per epic: ${(statsFeatures.length / statsEpics.length).toFixed(1)}`);
        break;

      default:
        console.log('Google Sheets Sync Service');
        console.log('');
        console.log('Usage:');
        console.log('  node scripts/sync-google-sheets.js fetch     - Fetch and display data');
        console.log('  node scripts/sync-google-sheets.js generate  - Generate FeatureRegistry.js');
        console.log('  node scripts/sync-google-sheets.js stats     - Show statistics');
        break;
    }
  } catch (error) {
    console.error('Error:', error.message);
    if (error.message.includes('permission')) {
      console.log('\nMake sure the spreadsheet is shared with:');
      console.log('metasfresh-e2e-sheets@feature-and-test-management.iam.gserviceaccount.com');
    }
    process.exit(1);
  }
}

main();
