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

  // A font glyph is sized by font-size and snaps to whole pixels: wide icons (warehouse) render bigger
  // and all sit lower than the SVG drawing they replaced. Indicators are drawn from the SVG files instead.
  it('draws every job-list indicator icon from its SVG file', () => {
    const indicatorSource = sources.find(({ fullPath }) => fullPath.endsWith('ButtonWithIndicator.jsx')).source;
    const indicatorIcons = [
      ...new Set([...indicatorSource.matchAll(/'indicator-box[^']*\bfas (fa-[a-z-]+)'/g)].map((match) => match[1])),
    ];
    const buttonsScss = fs.readFileSync(path.join(__dirname, '..', 'assets', 'buttons.scss'), 'utf8');

    expect(indicatorIcons.length).toBeGreaterThan(0);
    indicatorIcons.forEach((icon) => {
      expect(buttonsScss).toContain(`@include indicator-icon('${icon.replace(/^fa-/, '')}');`);
    });
  });

  it('never loads the SVG+JS build', () => {
    const offenders = sources
      .filter(({ source }) => /@fortawesome\/fontawesome-free\/js\//.test(source))
      .map(({ fullPath }) => fullPath);
    expect(offenders).toEqual([]);
  });
});
