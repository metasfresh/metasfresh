/**
 * Unified PDF Validator
 *
 * Combines text content validation and layout validation for metasfresh PDF documents.
 * This is the main utility that all PDF tests should use.
 *
 * Usage:
 *   const { PdfValidator } = require('../utils/PdfValidator');
 *   await PdfValidator.validate(download, {
 *     documentNo: 'SO-001',
 *     customerName: 'CUST001',
 *     productCode: 'PROD001',
 *     quantity: '10',
 *     language: 'en_US',
 *   });
 */

const { test } = require('../../playwright.config');
const { allure } = require('allure-playwright');
const { PdfLayoutValidator } = require('./PdfLayoutValidator');

class PdfValidator {
  /**
   * Normalize number for locale-independent comparison
   * Handles both "10.00" (English) and "10,00" (German)
   * @param {string|number} value - Value to normalize
   * @returns {number} Normalized number
   */
  static normalizeNumber(value) {
    const str = String(value).trim().replace(',', '.');
    return parseFloat(str);
  }

  /**
   * Validate PDF content (text) and layout (coordinates/overlaps)
   * @param {Download} download - Playwright Download object
   * @param {Object} options - Validation options
   * @param {string} options.documentNo - Document number to validate
   * @param {string} options.customerName - Customer name/code to validate
   * @param {string} [options.productCode] - Product code to validate (optional)
   * @param {string} [options.quantity] - Quantity to validate (optional, requires productCode)
   * @param {string} [options.language] - Language for logging (e.g., 'en_US', 'de_DE')
   * @param {boolean} [options.checkOverlaps=true] - Enable overlap detection
   * @param {boolean} [options.checkMargins=false] - Enable margin validation
   * @param {number} [options.overlapTolerance=2] - Overlap tolerance in pixels
   * @param {Object} [options.margins] - Margins for validation {top, right, bottom, left}
   * @param {Object} [options.pageSize] - Page size {width, height} in PDF units
   * @returns {Promise<void>} Throws error if validation fails
   */
  static async validate(download, options) {
    return await test.step('PdfValidator - Validate PDF', async () => {
      const fs = require('fs');
      const pdfParse = require('pdf-parse');

      // Extract options
      const {
        documentNo,
        customerName,
        productCode,
        quantity,
        language = 'unknown',
        checkOverlaps = true,
        checkMargins = false,
        overlapTolerance = 2,
        margins,
        pageSize,
        skipDocNumberValidation = false,
      } = options;

      // Validate required fields (unless skipped)
      if (!documentNo && !skipDocNumberValidation) {
        throw new Error('documentNo is required for PDF validation');
      }

      // Get file path
      const filePath = await download.path();
      const buffer = fs.readFileSync(filePath);

      console.log(`PDF file: ${await download.suggestedFilename()}`);
      console.log(`PDF file size: ${buffer.length} bytes`);

      // ============================================================
      // STEP 1: Parse PDF and extract text
      // ============================================================
      let pdfData;
      try {
        pdfData = await pdfParse(buffer);
      } catch (error) {
        console.error('Failed to parse PDF:', error.message);
        throw new Error(`PDF parsing failed: ${error.message}`);
      }

      console.log(`PDF pages: ${pdfData.numpages}`);
      console.log(`PDF text length: ${pdfData.text.length}`);

      // Extract and normalize text (replace multiple whitespaces with single space)
      const text = pdfData.text.replace(/\s+/g, ' ');
      // Also create a whitespace-stripped version for substring matching.
      // PDF text extraction often inserts spaces in the middle of long strings
      // (e.g., "Product1_20260301T08032 7689" instead of "Product1_20260301T080327689")
      // because the PDF renderer splits text across line fragments.
      const textNoSpaces = pdfData.text.replace(/\s+/g, '');

      // Log first 1000 chars for debugging
      console.log('PDF text preview:', text.substring(0, 1000));

      // ============================================================
      // STEP 2: Validate text content
      // ============================================================
      const errors = [];

      // Validate document number (unless skipped)
      if (documentNo && !skipDocNumberValidation) {
        if (!text.includes(documentNo)) {
          errors.push(
            `Document Number Validation Failed:\n` +
              `  Field: Document Number\n` +
              `  Expected: "${documentNo}"\n` +
              `  Actual: Not found in PDF`
          );
        } else {
          console.log('[PASS] Document number validated:', documentNo);
        }
      } else if (skipDocNumberValidation) {
        console.log('[INFO] Document number validation skipped (partial receipt)');
      }

      // Validate customer name/code (if provided)
      if (customerName) {
        if (!text.includes(customerName) && !textNoSpaces.includes(customerName)) {
          errors.push(
            `Customer Name Validation Failed:\n` +
              `  Field: Customer Name/Code\n` +
              `  Expected: "${customerName}"\n` +
              `  Actual: Not found in PDF`
          );
        } else {
          console.log('[PASS] Customer name validated:', customerName);
        }
      }

      // Validate product code (if provided)
      if (productCode) {
        if (!text.includes(productCode) && !textNoSpaces.includes(productCode)) {
          errors.push(
            `Product Code Validation Failed:\n` +
              `  Field: Product Code\n` +
              `  Expected: "${productCode}"\n` +
              `  Actual: Not found in PDF`
          );
        } else {
          console.log('[PASS] Product code validated:', productCode);
        }
      }

      // Validate quantity (if provided, requires productCode)
      if (quantity) {
        if (!productCode) {
          errors.push('Quantity validation requires productCode to be specified');
        } else {
          let productCodeIndex = text.indexOf(productCode);
          // Fall back to whitespace-stripped text if not found (PDF line-wrapping)
          const qtySearchText = productCodeIndex !== -1 ? text : textNoSpaces;
          if (productCodeIndex === -1) {
            productCodeIndex = textNoSpaces.indexOf(productCode);
          }
          if (productCodeIndex === -1) {
            errors.push('Product code not found in PDF text for quantity validation');
          } else {
            // Get text around the product (500 chars before and after should cover the order line)
            // Quantity may appear before or after the product code depending on PDF layout
            const startIndex = Math.max(0, productCodeIndex - 200);
            const endIndex = Math.min(qtySearchText.length, productCodeIndex + 500);
            const productLineText = qtySearchText.substring(startIndex, endIndex);

            // Try to match quantity with or without decimal places
            // Handles: "10", "10.00", "10,00", "1010" (position+qty), "10 Stk"
            // Pattern matches various formats where quantity appears
            const qtyRegex = new RegExp(`\\s(${quantity})\\s`);
            const qtyDecimalRegex = new RegExp(`\\s(${quantity}[.,]00)\\s`);
            // Also match "1010" pattern (Pos. 10, Menge 10) or "10Stk" pattern
            const qtyWithUnitRegex = new RegExp(`(${quantity})(Stk|St|Stück|pcs|pc|pieces)`);
            const qtyDoubleRegex = new RegExp(`(\\d+)(${quantity})\\s`);
            // Match price,00 followed by quantity followed by line number: "10,00510" = price(10,00) + qty(5) + line(10)
            // Pattern: comma or period followed by digits, then our quantity, then more digits
            const qtyAfterPriceRegex = new RegExp(`[.,]\\d{2}(${quantity})\\d`);
            // Match quantity followed by digits (line number) without space
            const qtyBeforeLineNoRegex = new RegExp(`(${quantity})\\d+\\s`);

            const quantityFound =
              qtyRegex.test(productLineText) ||
              qtyDecimalRegex.test(productLineText) ||
              qtyWithUnitRegex.test(productLineText) ||
              qtyDoubleRegex.test(productLineText) ||
              qtyAfterPriceRegex.test(productLineText) ||
              qtyBeforeLineNoRegex.test(productLineText);

            if (!quantityFound) {
              errors.push(
                `Quantity Validation Failed:\n` +
                  `  Field: Quantity\n` +
                  `  Expected: "${quantity}" (or "${quantity}.00" / "${quantity},00")\n` +
                  `  Actual: Not found in PDF near product "${productCode}"\n` +
                  `  Product line text: "${productLineText}"`
              );
            } else {
              console.log('[PASS] Quantity validated:', quantity);
            }
          }
        }
      }

      // If text validation failed, throw error
      if (errors.length > 0) {
        throw new Error(`PDF text validation failed:\n${errors.join('\n')}`);
      }

      // ============================================================
      // STEP 3: Validate layout (overlaps, margins)
      // ============================================================
      if (checkOverlaps || checkMargins) {
        const layoutOptions = {
          checkOverlaps,
          checkMargins,
          overlapTolerance,
          pageSize,
          margins,
        };

        const layoutResult = await PdfLayoutValidator.validate(buffer, layoutOptions);

        if (!layoutResult.valid) {
          console.log('PDF Layout Validation Failed:');
          console.log('Summary:', layoutResult.summary);
          console.log('Issues:', JSON.stringify(layoutResult.issues, null, 2));

          // Format error messages
          const errorMessages = layoutResult.issues.map((issue) => {
            if (issue.type === 'overlap') {
              return (
                `Overlap detected on page ${issue.page}: ` +
                `"${issue.text1}" and "${issue.text2}" overlap by ` +
                `${issue.overlapX.toFixed(1)}px (X) / ${issue.overlapY.toFixed(1)}px (Y)`
              );
            } else if (issue.type === 'margin') {
              return (
                `Margin violation on page ${issue.page}: ` +
                `"${issue.text}" violates ${issue.violation} ` +
                `(expected: ${issue.expected}, actual: ${issue.actual})`
              );
            }
            return JSON.stringify(issue);
          });

          throw new Error(`PDF layout validation failed:\n${errorMessages.join('\n')}`);
        }

        console.log('[PASS] PDF layout validated:', layoutResult.summary);
      }

      console.log('[PASS] PDF validation completed successfully for', language);

      // ============================================================
      // STEP 4: Attach PDF to Allure report
      // ============================================================
      try {
        const filename = await download.suggestedFilename();
        // Create descriptive name: e.g., "material-receipt-1000001.pdf"
        const attachmentName = documentNo ? `${filename.replace('.pdf', '')}-${documentNo}.pdf` : filename;

        await allure.attachment(attachmentName, buffer, 'application/pdf');
        console.log('[INFO] PDF attached to Allure report:', attachmentName);
      } catch (attachError) {
        // Don't fail the test if attachment fails, just log warning
        console.log('[WARN] Failed to attach PDF to Allure report:', attachError.message);
      }
    });
  }

  /**
   * Validate that a PDF buffer is a ZUGFeRD / Factur-X file by checking for an
   * embedded XML attachment named 'factur-x.xml' or 'zugferd-invoice.xml'.
   *
   * ZUGFeRD PDFs are PDF/A-3 files that embed the CII XML as a named file attachment.
   * pdfjs-dist exposes this via pdf.getAttachments(), which returns a map of
   * { filename: { filename, content } } entries or null when there are no attachments.
   *
   * This method asserts that at least one of the recognised attachment names is present.
   * It does NOT parse or validate the XML content — that is a backend responsibility.
   *
   * @param {Buffer} pdfBuffer - Raw PDF bytes (from the AD_Archive entry, NOT a re-render)
   * @returns {Promise<void>} Throws if no ZUGFeRD attachment is found
   */
  static async validateZugferdAttachment(pdfBuffer) {
    const pdfjsLib = require('pdfjs-dist');

    const uint8Array = new Uint8Array(pdfBuffer);
    const loadingTask = pdfjsLib.getDocument({ data: uint8Array });
    const pdf = await loadingTask.promise;

    console.log('[INFO] PDF pages:', pdf.numPages, '  — checking for ZUGFeRD attachment');

    const attachments = await pdf.getAttachments();

    if (!attachments) {
      throw new Error(
        'ZUGFeRD validation FAILED: pdf.getAttachments() returned null — ' +
        'no embedded file attachments found in this PDF. ' +
        'The CII XML was not embedded; verify that IsPdfA3Output=true is set and ' +
        'that the ZugferdAssembler interceptor ran at invoice completion.'
      );
    }

    // The attachment map keys are filenames (case-insensitive comparison)
    const attachmentNames = Object.keys(attachments).map((k) => k.toLowerCase());
    console.log('[INFO] PDF embedded attachments:', attachmentNames.join(', ') || '(none)');

    const ZUGFERD_ATTACHMENT_NAMES = ['factur-x.xml', 'zugferd-invoice.xml'];
    const found = ZUGFERD_ATTACHMENT_NAMES.find((name) => attachmentNames.includes(name));

    if (!found) {
      throw new Error(
        'ZUGFeRD validation FAILED: expected one of ' +
        JSON.stringify(ZUGFERD_ATTACHMENT_NAMES) +
        ' to be embedded in the PDF, but found: ' +
        JSON.stringify(attachmentNames) +
        '. The PDF is not a valid ZUGFeRD / Factur-X file.'
      );
    }

    console.log('[PASS] ZUGFeRD attachment found:', found);

    // Return the embedded CII XML so callers can assert the structured invoice content.
    // (The PDF/A-3 visual layer is not reliably text-extractable; the authoritative
    // e-invoice content lives in this CII XML.)
    const originalKey = Object.keys(attachments).find((k) => k.toLowerCase() === found);
    const ciiContent = attachments[originalKey].content;
    return Buffer.from(ciiContent).toString('utf-8');
  }
}

module.exports = { PdfValidator };
