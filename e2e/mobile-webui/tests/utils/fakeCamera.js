/**
 * Makes a camera available to the mobile UI in environments where the browser exposes none.
 *
 * `playwright.config.js` launches Chromium with `--use-fake-device-for-media-stream` so that
 * `getUserMedia()` succeeds without physical hardware. That synthetic device is only reachable
 * through `navigator.mediaDevices`, which is `[SecureContext]`-gated: it exists on
 * `http://localhost` but is UNDEFINED on every other plain-HTTP origin. CI serves the mobile UI
 * from `http://mobile:80/mobile` (see `docker-builds/e2e-tests/compose.yml`) — a non-localhost HTTP
 * origin — so there `navigator.mediaDevices` is undefined and the browser flag has no effect.
 *
 * The visible consequence: switching into camera mode does mount `CameraModePanel`, but ZXing's
 * `decodeFromVideoDevice()` rejects immediately, `onCancel` reverts to the default mode and
 * `.camera-mode-panel` unmounts again roughly 30 ms later. Camera-mode assertions were therefore
 * observing a 30 ms flicker rather than a settled camera mode, and passed or failed depending on
 * whether their first poll happened to fall inside that window.
 *
 * So when — and only when — the browser gives us no `navigator.mediaDevices`, install a minimal
 * media-device double backed by a repainted canvas stream: `getUserMedia()` resolves, the `<video>`
 * receives frames so `play()` resolves, ZXing keeps decoding, and camera mode stays active until
 * the operator leaves it. Whenever the browser does expose `mediaDevices` (any localhost run) the
 * real Chromium fake device is used unchanged.
 */
export const installFakeCameraIfBrowserExposesNone = async (page) =>
    await page.addInitScript(() => {
        if (navigator.mediaDevices?.getUserMedia) {
            return; // secure context: the browser's own fake media device is reachable
        }

        const newCameraStream = () => {
            const canvas = document.createElement('canvas');
            canvas.width = 640;
            canvas.height = 480;
            const context2d = canvas.getContext('2d');

            // Repaint continuously: a frame-starved captureStream() never fires `canplay`, which
            // leaves the <video>.play() promise pending forever inside ZXing.
            let frameNo = 0;
            const paintNextFrame = () => {
                frameNo++;
                context2d.fillStyle = frameNo % 2 === 0 ? '#202020' : '#303030';
                context2d.fillRect(0, 0, canvas.width, canvas.height);
            };
            paintNextFrame();
            const painterId = setInterval(paintNextFrame, 100);

            const stream = canvas.captureStream(10);
            // CameraModePanel stops the tracks when it unmounts — stop repainting with them.
            stream.getTracks().forEach((track) => {
                const stopTrack = track.stop.bind(track);
                track.stop = () => {
                    clearInterval(painterId);
                    stopTrack();
                };
            });
            return stream;
        };

        const fakeCamera = {
            deviceId: 'fake-camera',
            groupId: 'fake-camera-group',
            kind: 'videoinput',
            label: 'Fake camera',
        };
        const fakeMediaDevices = {
            getUserMedia: async () => newCameraStream(),
            enumerateDevices: async () => [{ ...fakeCamera, toJSON: () => fakeCamera }],
            getSupportedConstraints: () => ({ deviceId: true, facingMode: true, width: true, height: true }),
            addEventListener: () => {},
            removeEventListener: () => {},
            dispatchEvent: () => false,
        };
        Object.defineProperty(navigator, 'mediaDevices', { value: fakeMediaDevices, configurable: true });
    });
