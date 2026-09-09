/**
 * Image Widget Utility
 *
 * Handles Image widget type - binary data upload for images.
 * Used for BinaryData fields that store images.
 *
 * Features:
 * - Upload image from file
 * - Take photo from camera (if available)
 * - Clear/remove image
 * - Check if image is set
 *
 * Selectors:
 * - Container: .widgetType-Image.form-field-{fieldName}
 * - File input: input[type="file"] (hidden)
 * - Upload button: button with "Upload" or .input-dropzone
 * - Camera button: button with "camera"
 */
import { WidgetCommon } from './WidgetCommon';
import { getPage } from '../common';

export class ImageWidget {
  /**
   * Upload an image file.
   *
   * @param {string} fieldName - Database column name (e.g., 'BinaryData')
   * @param {string} filePath - Absolute path to the image file
   */
  static async uploadFile(fieldName, filePath) {
    return await WidgetCommon.withStep(`ImageWidget - Upload file to ${fieldName}`, async () => {
      const page = getPage();

      // Use widgetType-specific selector for Image widgets
      const container = WidgetCommon.getFieldContainer(fieldName, 'Image');

      // The Image component has a label that wraps the hidden file input
      // Clicking the label triggers the native file chooser
      // Structure: <label><input type="file" /><div>Upload Photo</div></label>
      const uploadLabel = container.locator('label.btn, label:has(input[type="file"])').first();

      if (await uploadLabel.count() > 0) {
        console.log(`ImageWidget - Using label-click approach for ${fieldName}`);

        // Dismiss any focused fields by clicking elsewhere first
        await page.keyboard.press('Escape');
        await page.waitForTimeout(200);

        // Click on a neutral area to blur any focused input fields (like address fields)
        // This prevents "element intercepts pointer events" errors
        await page.locator('body').click({ position: { x: 10, y: 10 }, force: true });
        await page.waitForTimeout(300);

        // Scroll the upload label into view to avoid overlapping elements
        await uploadLabel.scrollIntoViewIfNeeded();
        await page.waitForTimeout(200);

        // Set up promises to monitor network requests BEFORE triggering upload
        const uploadPromise = page.waitForResponse(
          response => response.url().includes('/rest/api/image') && response.request().method() === 'POST',
          { timeout: 30000 }
        ).catch(e => {
          console.log('No /rest/api/image POST request detected:', e.message);
          return null;
        });

        // Also monitor for PATCH request that saves the imageId to the record
        const patchPromise = page.waitForResponse(
          response => response.url().includes('/rest/api/window/') && response.request().method() === 'PATCH',
          { timeout: 30000 }
        ).catch(e => {
          console.log('No PATCH request to save field detected:', e.message);
          return null;
        });

        // Set up file chooser handler BEFORE clicking the label
        const fileChooserPromise = page.waitForEvent('filechooser', { timeout: 10000 });

        // Click the label to open file chooser
        await uploadLabel.click();

        // Wait for file chooser and set the file
        const fileChooser = await fileChooserPromise;
        await fileChooser.setFiles(filePath);

        console.log(`ImageWidget - File selected, waiting for upload...`);

        // Wait for the upload request to complete
        const uploadResponse = await uploadPromise;
        if (uploadResponse) {
          const imageId = await uploadResponse.json().catch(() => null);
          console.log(`ImageWidget - Upload response (imageId): ${JSON.stringify(imageId)}`);

          // Wait for PATCH to save the imageId to the record
          const patchResponse = await patchPromise;
          if (patchResponse) {
            console.log(`ImageWidget - PATCH request completed (field value saved)`);
          } else {
            console.log(`ImageWidget - WARNING: No PATCH request detected. ImageId may not be saved to record.`);
          }
        } else {
          console.log(`ImageWidget - WARNING: No upload request detected. File may not have been uploaded.`);
        }

        // Wait for any spinners to disappear
        await page.waitForTimeout(500);
        await WidgetCommon.waitForSaveComplete();
      } else {
        // Fallback: Try to find the file input directly
        const fileInput = container.locator('input[type="file"]').first();

        if (await fileInput.count() > 0) {
          console.log(`ImageWidget - Using setInputFiles fallback for ${fieldName}`);

          // Set up file chooser handler
          const fileChooserPromise = page.waitForEvent('filechooser', { timeout: 10000 }).catch(() => null);

          // Make sure the input is visible/interactive
          await fileInput.evaluate(el => {
            el.style.opacity = '1';
            el.style.display = 'block';
            el.style.visibility = 'visible';
          });

          // Click the input to open file chooser
          await fileInput.click();

          const fileChooser = await fileChooserPromise;
          if (fileChooser) {
            await fileChooser.setFiles(filePath);
          } else {
            // Direct setInputFiles as last resort
            await fileInput.setInputFiles(filePath);
          }

          await page.waitForTimeout(2000);
          await WidgetCommon.waitForSaveComplete();
        } else {
          console.log(`ImageWidget - No file input found for ${fieldName}`);
        }
      }
    });
  }

  /**
   * Click the upload button to open file chooser.
   * Use with Playwright's page.on('filechooser') for custom handling.
   *
   * @param {string} fieldName - Database column name
   */
  static async clickUpload(fieldName) {
    return await WidgetCommon.withStep(`ImageWidget - Click upload for ${fieldName}`, async () => {
      // Use widgetType-specific selector for Image widgets
      const container = WidgetCommon.getFieldContainer(fieldName, 'Image');
      const uploadButton = container.locator('button:has-text("Upload"), .input-dropzone').first();
      await uploadButton.click();
    });
  }

  /**
   * Clear/remove the current image.
   *
   * @param {string} fieldName - Database column name
   */
  static async clear(fieldName) {
    return await WidgetCommon.withStep(`ImageWidget - Clear ${fieldName}`, async () => {
      // Use widgetType-specific selector for Image widgets
      const container = WidgetCommon.getFieldContainer(fieldName, 'Image');

      // Find and click the remove/clear button
      const clearButton = container.locator('button:has-text("Remove"), button:has-text("Clear"), [class*="close"], [class*="delete"]').first();

      if (await clearButton.count() > 0 && await clearButton.isVisible()) {
        await clearButton.click();
        await WidgetCommon.waitForSaveComplete();
      } else {
        console.log(`ImageWidget - No clear button found for ${fieldName}`);
      }
    });
  }

  /**
   * Check if an image is currently set.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<boolean>} True if an image is set
   */
  static async hasImage(fieldName) {
    return await WidgetCommon.withStep(`ImageWidget - Check if ${fieldName} has image`, async () => {
      // Use widgetType-specific selector for Image widgets
      const container = WidgetCommon.getFieldContainer(fieldName, 'Image');

      // Check for image element with valid src
      const image = container.locator('.image-preview img').first();
      const hasImg = await image.count() > 0;

      if (hasImg) {
        const src = await image.getAttribute('src');
        // Valid image sources start with data: (base64) or http
        const hasValidSrc = !!src && (src.startsWith('data:') || src.startsWith('http'));
        console.log(`ImageWidget - hasImage(${fieldName}): found img with src starting with "${src?.substring(0, 30)}...": ${hasValidSrc}`);
        return hasValidSrc;
      }

      // If no image preview, check if placeholder shows empty state
      const placeholder = container.locator('.image-placeholder').first();
      if (await placeholder.count() > 0) {
        console.log(`ImageWidget - hasImage(${fieldName}): found placeholder (no image)`);
        return false;
      }

      // Check if there's an image-preview div at all
      const previewDiv = container.locator('.image-preview').first();
      if (await previewDiv.count() > 0) {
        console.log(`ImageWidget - hasImage(${fieldName}): found image-preview div`);
        return true;
      }

      console.log(`ImageWidget - hasImage(${fieldName}): no image or placeholder found`);
      return false;
    });
  }

  /**
   * Check if image field is empty.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<boolean>} True if no image is set
   */
  static async isEmpty(fieldName) {
    return !(await this.hasImage(fieldName));
  }
}
