import fs from 'fs';
import path from 'path';

// The app must use FontAwesome's CSS web font: the SVG+JS build (js/all*) rewrites every <i class="fa…">
// into an <svg> on each DOM change, which stalls long lists on handhelds.
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
