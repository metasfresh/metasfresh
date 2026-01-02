#!/usr/bin/env node
/**
 * Update Allure annotations in mobile-webui spec files.
 * Adds epic/feature names to allure.epic() and allure.tag() calls.
 */

const fs = require('fs');
const path = require('path');

// Epic ID to name mappings
const EPICS = {
  'E0370': 'Intralogistic (HUs)',
  'E0295': 'Frontend MobileUI',
  'E0105': 'Picking',
  'E0160': 'Manufacturing Execution',
};

// Feature ID to name mappings
const FEATURES = {
  'F5114': 'MobileUI Distribution',
  'F12000': 'Frontend MobileUI',
  'F8030': 'MobileUI Manufacturing',
  'F00230': 'MobileUI Picking',
  'F5112': 'One QR for many HUs',
  'F5112.1': 'One QR for many HUs',
  'F4065': 'MobileUI HU Manager',
  'F9500': 'MobileUI Inventory',
};

function findSpecFiles(dir) {
  const files = [];
  const items = fs.readdirSync(dir, { withFileTypes: true });
  for (const item of items) {
    const fullPath = path.join(dir, item.name);
    if (item.isDirectory()) {
      files.push(...findSpecFiles(fullPath));
    } else if (item.name.endsWith('.spec.js')) {
      files.push(fullPath);
    }
  }
  return files;
}

function processFile(filePath, dryRun) {
  let content = fs.readFileSync(filePath, 'utf8');
  let modified = false;
  const changes = [];

  // Update allure.epic('EXXXX') -> allure.epic('EXXXX: Name')
  content = content.replace(/allure\.epic\('(E\d+)'\)/g, (match, epicId) => {
    const epicName = EPICS[epicId];
    if (epicName) {
      const newCall = `allure.epic('${epicId}: ${epicName}')`;
      changes.push(`  epic: ${match} -> ${newCall}`);
      modified = true;
      return newCall;
    }
    return match;
  });

  // Update allure.tag('FXXXX') -> allure.tag('FXXXX: Name') followed by standalone tag
  // But only if it doesn't already have a name
  content = content.replace(/allure\.tag\('(F[\d.]+)'\)(?!\s*;\s*\/\/\s*Standalone)/g, (match, featureId) => {
    const featureName = FEATURES[featureId];
    if (featureName) {
      const newCall = `allure.tag('${featureId}: ${featureName}');\n        allure.tag('${featureId}');  // Standalone tag for Tags section`;
      changes.push(`  feature: ${match} -> allure.tag('${featureId}: ${featureName}') + standalone`);
      modified = true;
      return newCall;
    }
    return match;
  });

  if (modified) {
    console.log('\n' + path.relative(process.cwd(), filePath) + ':');
    changes.forEach(c => console.log(c));
    if (!dryRun) {
      fs.writeFileSync(filePath, content, 'utf8');
      console.log('  -> Updated');
    } else {
      console.log('  -> (dry-run)');
    }
  }
  return modified;
}

function main() {
  const dryRun = process.argv.includes('--dry-run');

  const mobileDir = path.join(__dirname, '../../e2e/mobile-webui/tests/spec');
  console.log('Scanning ' + mobileDir + '...');

  const files = findSpecFiles(mobileDir);
  console.log('Found ' + files.length + ' spec files');

  if (dryRun) {
    console.log('\n=== DRY RUN MODE ===\n');
  }

  let modifiedCount = 0;
  for (const file of files) {
    if (processFile(file, dryRun)) {
      modifiedCount++;
    }
  }

  console.log('\n' + (dryRun ? 'Would modify' : 'Modified') + ' ' + modifiedCount + ' files');
}

main();
