/**
 * Google Sheets Inspection Script
 *
 * Reads the Epic and Feature sheets to understand their structure.
 * This helps design the FeatureRegistry and Allure integration.
 *
 * Usage: node scripts/inspect-google-sheets.js
 */

const { google } = require('googleapis');
const fs = require('fs');
const path = require('path');

// Configuration
const SPREADSHEET_ID = '1HYDaiNZVseCg4WtIaxJQ-LLclNl7vkHXp6WsMEaKK9A';
const CREDENTIALS_PATH = path.join(__dirname, '../credentials/google-sheets-service-account.json');

async function main() {
  console.log('='.repeat(80));
  console.log('Google Sheets Structure Inspection');
  console.log('='.repeat(80));
  console.log();

  // Load credentials
  if (!fs.existsSync(CREDENTIALS_PATH)) {
    console.error(`Credentials file not found at: ${CREDENTIALS_PATH}`);
    process.exit(1);
  }

  const credentials = JSON.parse(fs.readFileSync(CREDENTIALS_PATH, 'utf8'));
  console.log(`Service Account: ${credentials.client_email}`);
  console.log(`Spreadsheet ID: ${SPREADSHEET_ID}`);
  console.log();

  // Authenticate
  const auth = new google.auth.GoogleAuth({
    credentials,
    scopes: ['https://www.googleapis.com/auth/spreadsheets.readonly'],
  });

  const sheets = google.sheets({ version: 'v4', auth });

  // First, get spreadsheet metadata to see all sheet names
  console.log('Fetching spreadsheet metadata...');
  const spreadsheet = await sheets.spreadsheets.get({
    spreadsheetId: SPREADSHEET_ID,
  });

  console.log('\n--- Available Sheets ---');
  spreadsheet.data.sheets.forEach((sheet, index) => {
    console.log(`${index + 1}. "${sheet.properties.title}" (ID: ${sheet.properties.sheetId})`);
  });
  console.log();

  // Inspect Epic sheet
  await inspectSheet(sheets, 'Epic');

  // Inspect Feature sheet
  await inspectSheet(sheets, 'Feature');
}

async function inspectSheet(sheets, sheetName) {
  console.log('='.repeat(80));
  console.log(`Sheet: "${sheetName}"`);
  console.log('='.repeat(80));

  try {
    // Fetch all data from the sheet
    const response = await sheets.spreadsheets.values.get({
      spreadsheetId: SPREADSHEET_ID,
      range: `${sheetName}!A1:Z1000`, // Fetch up to column Z and 1000 rows
    });

    const rows = response.data.values || [];

    if (rows.length === 0) {
      console.log('No data found in sheet.');
      return;
    }

    // First row is headers
    const headers = rows[0];
    const dataRows = rows.slice(1);

    console.log(`\nTotal rows: ${dataRows.length}`);
    console.log(`Columns: ${headers.length}`);
    console.log('\n--- Column Headers ---');
    headers.forEach((header, index) => {
      // Count non-empty values in this column
      const nonEmptyCount = dataRows.filter(row => row[index] && row[index].trim()).length;
      console.log(`  ${String.fromCharCode(65 + index)}. "${header}" (${nonEmptyCount} values)`);
    });

    // Show first 5 rows of data
    console.log('\n--- Sample Data (first 5 rows) ---');
    const sampleRows = dataRows.slice(0, 5);
    sampleRows.forEach((row, rowIndex) => {
      console.log(`\nRow ${rowIndex + 2}:`);
      headers.forEach((header, colIndex) => {
        const value = row[colIndex] || '';
        const displayValue = value.length > 60 ? value.substring(0, 60) + '...' : value;
        if (displayValue) {
          console.log(`  ${header}: ${displayValue}`);
        }
      });
    });

    // Analyze unique values for ID columns
    console.log('\n--- Column Analysis ---');
    headers.forEach((header, index) => {
      const values = dataRows.map(row => row[index]).filter(v => v && v.trim());
      const uniqueValues = [...new Set(values)];

      // Check if it looks like an ID column
      if (header.toLowerCase().includes('id') || header.toLowerCase().includes('nr') || header.toLowerCase().includes('no')) {
        console.log(`\n"${header}" appears to be an ID column:`);
        console.log(`  Unique values: ${uniqueValues.length}`);
        console.log(`  Sample IDs: ${uniqueValues.slice(0, 5).join(', ')}${uniqueValues.length > 5 ? '...' : ''}`);
      }
    });

    console.log('\n');

  } catch (error) {
    console.error(`Error reading sheet "${sheetName}":`, error.message);
    if (error.message.includes('not have permission')) {
      console.log('\nMake sure you have shared the spreadsheet with:');
      console.log('metasfresh-e2e-sheets@feature-and-test-management.iam.gserviceaccount.com');
    }
  }
}

main().catch(console.error);
