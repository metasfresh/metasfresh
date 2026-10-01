import fs from 'fs';
import path from 'path';

// FontAwesome's SVG+JS build (js/all*) watches the whole document with a MutationObserver and rewrites
// every <i class="fa…"> into an <svg>. On a long job list that stalls the main thread for minutes on a
// handheld, so the app must use the CSS web-font build, which draws the same <i> tags without any JS.
const readSources = (dir) =>
  fs.readdirSync(dir, { withFileTypes: true }).flatMap((entry) => {
    const fullPath = path.join(dir, entry.name);
    if (entry.isDirectory()) return entry.name === '__tests__' ? [] : readSources(fullPath);
    return /\.(js|jsx)$/.test(entry.name) ? [{ fullPath, source: fs.readFileSync(fullPath, 'utf8') }] : [];
  });

describe('FontAwesome', () => {
  const sources = readSources(path.join(__dirname, '..'));

  it('is loaded as the CSS web-font build', () => {
    const indexSource = sources.find(({ fullPath }) => fullPath.endsWith(`${path.sep}src${path.sep}index.js`)).source;
    expect(indexSource).toContain("import '@fortawesome/fontawesome-free/css/all.css';");
  });

  it('never loads the SVG+JS build', () => {
    const offenders = sources
      .filter(({ source }) => /@fortawesome\/fontawesome-free\/js\//.test(source))
      .map(({ fullPath }) => fullPath);
    expect(offenders).toEqual([]);
  });
});
