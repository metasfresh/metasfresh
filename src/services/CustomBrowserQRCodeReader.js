import { BrowserQRCodeReader } from '@zxing/library';
import Timer from 'timer-machine';

class ScannerError extends Error {}

/**
 *
 * QR Code reader to use from browser.
 */
export default class CustomBrowserQRCodeReader extends BrowserQRCodeReader {
  /**
   * 
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
    throw new ScannerError("No QR Code found. Fall back to barcode scanner")
  }
}
