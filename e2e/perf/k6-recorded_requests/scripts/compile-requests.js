/*
 * #%L
 * master
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/* eslint-disable no-console */
const fs = require('fs');
const path = require('path');

function isJsonFile(name) {
  return name.toLowerCase().endsWith('.json');
}

function readJson(filePath) {
  const content = fs.readFileSync(filePath, 'utf8');
  try {
    return JSON.parse(content);
  } catch (e) {
    throw new Error(`Failed to parse JSON file ${filePath}: ${e.message}`);
  }
}

function validateRequest(obj, filePath) {
  if (!obj || typeof obj !== 'object') {
    throw new Error(`Invalid request in ${filePath}: expected an object`);
  }
  if (!obj.method || typeof obj.method !== 'string') {
    throw new Error(`Invalid request in ${filePath}: "method" is required and must be a string`);
  }
  if (!obj.path || typeof obj.path !== 'string') {
    throw new Error(`Invalid request in ${filePath}: "path" is required and must be a string`);
  }
  // body is optional; headers optional
  return obj;
}

function main() {
  const cwd = process.cwd();
  const reqDir = process.env.REQ_DIR
    ? path.resolve(cwd, process.env.REQ_DIR)
    : path.resolve(cwd, 'requests');

  if (!fs.existsSync(reqDir) || !fs.statSync(reqDir).isDirectory()) {
    console.error(`Requests directory not found: ${reqDir}`);
    process.exit(1);
  }

  const entries = fs.readdirSync(reqDir);
  const files = entries
    .filter((n) => isJsonFile(n))
    .sort((a, b) => a.localeCompare(b));

  if (files.length === 0) {
    console.error(`No *.json files found in ${reqDir}`);
    process.exit(1);
  }

  console.log(`Found ${files.length} JSON files in ${reqDir}:`);
  files.forEach((f, i) => console.log(`  ${String(i + 1).padStart(3, ' ')}. ${f}`));

  const requests = files.map((filename) => {
    const full = path.join(reqDir, filename);
    const obj = readJson(full);
    return validateRequest(obj, full);
  });

  const outPath = path.resolve(cwd, 'compiled-requests.json');
  fs.writeFileSync(outPath, JSON.stringify(requests, null, 2), 'utf8');
  console.log(`\nWrote ${requests.length} requests to ${outPath}`);
}

if (require.main === module) {
  try {
    main();
  } catch (err) {
    console.error(err.message || err);
    process.exit(1);
  }
}
