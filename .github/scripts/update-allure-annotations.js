#!/usr/bin/env node
/**
 * Update Allure annotations in Cucumber feature files.
 * Uses only fs module for file operations - no shell commands.
 */

const fs = require('fs');
const path = require('path');

const registryPath = path.join(__dirname, '../../../.claude/skills/google-sheets-sync/FeatureRegistry.generated.js');

function loadRegistry() {
  const content = fs.readFileSync(registryPath, 'utf8');
  const features = {};
  const epics = {};

  const featurePattern = /'(F\d+)':\s*\{[^}]*id:\s*'(F\d+)'[^}]*name:\s*'([^']+)'/g;
  let match;
  while ((match = featurePattern.exec(content)) !== null) {
    features[match[1]] = { id: match[2], name: match[3] };
  }

  const epicPattern = /'([^']+)':\s*\{[^}]*id:\s*'(E\d+)'[^}]*name:\s*'([^']+)'/g;
  while ((match = epicPattern.exec(content)) !== null) {
    if (match[2].startsWith('E')) {
      epics[match[2]] = { id: match[2], name: match[3] };
    }
  }

  return { features, epics };
}

function toTagSafe(name) {
  return name
    .replace(/[^a-zA-Z0-9\s]/g, '')
    .replace(/\s+/g, '_')
    .replace(/_+/g, '_')
    .replace(/^_|_$/g, '');
}

function findFeatureFiles(dir) {
  const files = [];
  const items = fs.readdirSync(dir, { withFileTypes: true });
  for (const item of items) {
    const fullPath = path.join(dir, item.name);
    if (item.isDirectory()) {
      files.push(...findFeatureFiles(fullPath));
    } else if (item.name.endsWith('.feature')) {
      files.push(fullPath);
    }
  }
  return files;
}

function processFile(filePath, registry, dryRun) {
  let content = fs.readFileSync(filePath, 'utf8');
  let modified = false;
  const changes = [];
  const featureIdsToAdd = new Set();

  content = content.replace(/@allure\.label\.feature:(F\d+)(?!_)/g, (match, featureId) => {
    const feature = registry.features[featureId];
    if (feature) {
      const newTag = '@allure.label.feature:' + featureId + '_' + toTagSafe(feature.name);
      featureIdsToAdd.add(featureId);
      changes.push('  feature: ' + match + ' -> ' + newTag);
      modified = true;
      return newTag;
    }
    return match;
  });

  content = content.replace(/@allure\.label\.epic:(E\d+)(?!_)/g, (match, epicId) => {
    const epic = registry.epics[epicId];
    if (epic) {
      const newTag = '@allure.label.epic:' + epicId + '_' + toTagSafe(epic.name);
      changes.push('  epic: ' + match + ' -> ' + newTag);
      modified = true;
      return newTag;
    }
    return match;
  });

  for (const featureId of featureIdsToAdd) {
    const standalonePattern = new RegExp('@' + featureId + '(?![_\\d])', 'g');
    if (!standalonePattern.test(content)) {
      const featureLinePattern = new RegExp('(@allure\\.label\\.feature:' + featureId + '_[^\\s]+)', 'g');
      content = content.replace(featureLinePattern, '$1\n@' + featureId);
      changes.push('  added standalone: @' + featureId);
      modified = true;
    }
  }

  if (modified) {
    console.log('\n' + path.relative(process.cwd(), filePath) + ':');
    changes.forEach(function(c) { console.log(c); });
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
  console.log('Loading feature registry...');
  const registry = loadRegistry();
  console.log('Found ' + Object.keys(registry.features).length + ' features, ' + Object.keys(registry.epics).length + ' epics');

  const featuresDir = path.join(__dirname, '../../backend/de.metas.cucumber/src/test/resources/de/metas/cucumber/features');
  console.log('\nScanning ' + featuresDir + '...');
  const files = findFeatureFiles(featuresDir);
  console.log('Found ' + files.length + ' feature files');

  if (dryRun) {
    console.log('\n=== DRY RUN MODE ===\n');
  }

  let modifiedCount = 0;
  for (let i = 0; i < files.length; i++) {
    if (processFile(files[i], registry, dryRun)) {
      modifiedCount++;
    }
  }

  console.log('\n' + (dryRun ? 'Would modify' : 'Modified') + ' ' + modifiedCount + ' files');
}

main();
