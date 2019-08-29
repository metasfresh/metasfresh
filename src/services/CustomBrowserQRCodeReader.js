import { BrowserQRCodeReader } from '@zxing/library';
import Timer from 'timer-machine';

class ScannerError extends Error {}

/**
 * @file QR Code reader to use from browser.
 * @module CustomBrowserQRCodeReader
 * @extends BrowserQRCodeReader
 */
export default class CustomBrowserQRCodeReader extends BrowserQRCodeReader {
  /**
   * Extend the built-in method. Throw a custom error when QR code not found during
   * the timeout time.
   * @method readerDecode
   * @param {*} binaryBitmap
   */
  readerDecode(binaryBitmap) {
    if (!this.timer) {
      this.timer = new Timer();
      this.timer.start();
    }

    if (this.timer.time() < 3000) {
      return super.readerDecode(binaryBitmap);
    }

    this.timer.destroy();
    throw new ScannerError('No QR Code found.');
  }
}
