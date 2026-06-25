// Playwright globalTeardown — auto-prepare + caption UAT videos after EVERY run.
//
// Shared by both e2e suites (frontend-webui and mobile-webui). This is the "no human
// asking, every run" delivery step: for each recorded video it produces a slowed, trimmed,
// step-captioned COPY under <suite>/uat-videos/, via the playwright-video-delivery skill's
// pipeline (prepare-uat-videos.sh — which auto-generates the captions from the run's trace).
//
// Design notes:
//  - We operate on COPIES, never the raw test-results/ files: the html + allure reporters
//    consume the raw video.webm/trace.zip (their onEnd can run after globalTeardown), so
//    mutating them in place could corrupt the report. uat-videos/ is a clean, predictable
//    output dir for UAT pickup.
//  - It processes EVERY recorded video. For a large CI suite that is a lot of ffmpeg work —
//    set UAT_VIDEO_PREP=0 to skip entirely (fast debug loops / bulk CI runs).
//  - The pipeline script lives in the playwright-video-delivery skill, installed under the
//    workspace .claude/ on a dev machine; it is ABSENT in a bare CI checkout. When it can't
//    be found (or python3/ffmpeg are missing) this teardown skips with a warning.
//  - NEVER fails the run: everything is wrapped so a prep problem only warns; the test
//    pass/fail result is untouched.
const fs = require('node:fs');
const path = require('node:path');
const { execFileSync } = require('node:child_process');

module.exports = async (config) => {
  try {
    if (process.env.UAT_VIDEO_PREP === '0') {
      console.log('[uat-video-prep] skipped (UAT_VIDEO_PREP=0)');
      return;
    }

    // config.rootDir is the suite's config dir (e2e/<suite>), so this one shared file works
    // for both suites without knowing which it runs in.
    const suiteDir = config && config.rootDir ? config.rootDir : __dirname;
    const resultsDir = path.join(suiteDir, 'test-results');
    if (!fs.existsSync(resultsDir)) return; // nothing was recorded

    // Resolve the playwright-video-delivery pipeline script. Dev machine: workspace-root
    // .claude/. Absent in a bare checkout / CI -> skip gracefully.
    const repoRoot = path.resolve(suiteDir, '..', '..'); // metasfresh repo root
    const rel = '.claude/skills/playwright-video-delivery/scripts/prepare-uat-videos.sh';
    const candidates = [
      process.env.UAT_VIDEO_PREP_SCRIPT,
      path.resolve(repoRoot, '..', rel), // workspace root (one level above the repo)
      path.resolve(repoRoot, rel),       // inside the repo, if ever installed there
    ].filter(Boolean);
    const script = candidates.find((p) => fs.existsSync(p));
    if (!script) {
      console.warn(
        '[uat-video-prep] prepare-uat-videos.sh not found (playwright-video-delivery skill not installed) — skipping UAT video prep.'
      );
      return;
    }

    // Collect every recorded video.webm under test-results/.
    const videos = [];
    const walk = (dir) => {
      for (const e of fs.readdirSync(dir, { withFileTypes: true })) {
        const p = path.join(dir, e.name);
        if (e.isDirectory()) walk(p);
        else if (e.name.endsWith('.webm')) videos.push(p);
      }
    };
    walk(resultsDir);
    if (videos.length === 0) return;

    // Copy each video (+ its sibling trace.zip, so the pipeline auto-captions) into uat-videos/,
    // named after its test dir so multiple videos never collide.
    const outDir = path.join(suiteDir, 'uat-videos');
    fs.mkdirSync(outDir, { recursive: true });
    const prepared = [];
    for (const video of videos) {
      const testDir = path.basename(path.dirname(video));
      const base = testDir && testDir !== 'test-results' ? testDir : path.basename(video, '.webm');
      const destVideo = path.join(outDir, `${base}.webm`);
      fs.copyFileSync(video, destVideo);
      const trace = path.join(path.dirname(video), 'trace.zip');
      if (fs.existsSync(trace)) fs.copyFileSync(trace, path.join(outDir, `${base}.trace.zip`));
      prepared.push(destVideo);
    }

    // One invocation prepares + auto-captions all the copies (it is idempotent).
    execFileSync('bash', [script, ...prepared], { stdio: 'inherit' });
    console.log(`[uat-video-prep] prepared ${prepared.length} video(s) in ${path.relative(suiteDir, outDir)}/`);
  } catch (err) {
    // Never fail the run because of video prep.
    console.warn(`[uat-video-prep] skipped (non-fatal): ${err && err.message ? err.message : err}`);
  }
};
