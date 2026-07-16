/**
 * PDF Layout Validator
 *
 * Uses PDF.js to extract text coordinates and detect layout errors like overlapping text.
 * This utility provides low-level coordinate analysis for PDF documents.
 */

class PdfLayoutValidator {
  /**
   * Extract text elements with coordinates from PDF
   * @param {Buffer} pdfBuffer - PDF file buffer
   * @returns {Promise<Array<{text, x, y, width, height, pageNum}>>} Text items with coordinates
   */
  static async extractTextCoordinates(pdfBuffer) {
    const pdfjsLib = require('pdfjs-dist');

    // Convert Buffer to Uint8Array (required by pdfjs-dist)
    const uint8Array = new Uint8Array(pdfBuffer);

    // Load PDF document
    const loadingTask = pdfjsLib.getDocument({ data: uint8Array });
    const pdf = await loadingTask.promise;

    const textItems = [];

    // Extract text from each page
    for (let pageNum = 1; pageNum <= pdf.numPages; pageNum++) {
      const page = await pdf.getPage(pageNum);
      const textContent = await page.getTextContent();

      // Each item has: str (text), transform (position matrix), width, height
      textContent.items.forEach((item) => {
        textItems.push({
          text: item.str,
          x: item.transform[4], // X position
          y: item.transform[5], // Y position
          width: item.width, // Width of text
          height: item.height, // Height of text
          pageNum, // Page number
        });
      });
    }

    return textItems;
  }

  /**
   * Detect overlapping text elements
   * @param {Array} textItems - Text items with coordinates
   * @param {number} tolerance - Overlap tolerance in pixels (default: 2)
   * @returns {Array} Overlap issues with details
   */
  static detectOverlaps(textItems, tolerance = 2) {
    const overlaps = [];

    // Check each pair of text items
    for (let i = 0; i < textItems.length; i++) {
      for (let j = i + 1; j < textItems.length; j++) {
        const item1 = textItems[i];
        const item2 = textItems[j];

        // Skip if on different pages
        if (item1.pageNum !== item2.pageNum) continue;

        // Check for bounding box overlap
        const overlapX = Math.max(
          0,
          Math.min(item1.x + item1.width, item2.x + item2.width) - Math.max(item1.x, item2.x)
        );

        const overlapY = Math.max(
          0,
          Math.min(item1.y + item1.height, item2.y + item2.height) - Math.max(item1.y, item2.y)
        );

        // If overlap exceeds tolerance in BOTH dimensions, record it (true 2D overlap)
        if (overlapX > tolerance && overlapY > tolerance) {
          overlaps.push({
            text1: item1.text,
            text2: item2.text,
            overlapX,
            overlapY,
            page: item1.pageNum,
            item1Coords: { x: item1.x, y: item1.y, width: item1.width, height: item1.height },
            item2Coords: { x: item2.x, y: item2.y, width: item2.width, height: item2.height },
          });
        }
      }
    }

    return overlaps;
  }

  /**
   * Validate text stays within page margins
   * @param {Array} textItems - Text items with coordinates
   * @param {Object} pageSize - { width, height } in PDF units
   * @param {Object} margins - { top, right, bottom, left } in PDF units
   * @returns {Array} Margin violations
   */
  static validateMargins(textItems, pageSize, margins) {
    const violations = [];

    textItems.forEach((item) => {
      // Check if text extends beyond margins
      if (item.x < margins.left) {
        violations.push({
          text: item.text,
          violation: 'left margin',
          expected: margins.left,
          actual: item.x,
          page: item.pageNum,
        });
      }

      if (item.x + item.width > pageSize.width - margins.right) {
        violations.push({
          text: item.text,
          violation: 'right margin',
          expected: pageSize.width - margins.right,
          actual: item.x + item.width,
          page: item.pageNum,
        });
      }

      if (item.y < margins.bottom) {
        violations.push({
          text: item.text,
          violation: 'bottom margin',
          expected: margins.bottom,
          actual: item.y,
          page: item.pageNum,
        });
      }

      if (item.y + item.height > pageSize.height - margins.top) {
        violations.push({
          text: item.text,
          violation: 'top margin',
          expected: pageSize.height - margins.top,
          actual: item.y + item.height,
          page: item.pageNum,
        });
      }
    });

    return violations;
  }

  /**
   * Main validation function
   * @param {Buffer} pdfBuffer - PDF file buffer
   * @param {Object} options - Validation options
   * @returns {Promise<{valid: boolean, issues: Array, summary: Object}>} Validation result
   */
  static async validate(pdfBuffer, options = {}) {
    const {
      checkOverlaps = true,
      checkMargins = false,
      overlapTolerance = 2,
      pageSize = { width: 595, height: 842 }, // A4 in PDF units
      margins = { top: 20, right: 20, bottom: 20, left: 20 },
    } = options;

    const issues = [];

    // Extract text coordinates
    const textItems = await this.extractTextCoordinates(pdfBuffer);
    console.log(`Extracted ${textItems.length} text items from PDF`);

    // Check overlaps
    if (checkOverlaps) {
      const overlaps = this.detectOverlaps(textItems, overlapTolerance);
      if (overlaps.length > 0) {
        issues.push(
          ...overlaps.map((o) => ({
            type: 'overlap',
            severity: 'error',
            ...o,
          }))
        );
      }
    }

    // Check margins
    if (checkMargins) {
      const violations = this.validateMargins(textItems, pageSize, margins);
      if (violations.length > 0) {
        issues.push(
          ...violations.map((v) => ({
            type: 'margin',
            severity: 'warning',
            ...v,
          }))
        );
      }
    }

    return {
      valid: issues.length === 0,
      issues,
      summary: {
        totalTextItems: textItems.length,
        overlaps: issues.filter((i) => i.type === 'overlap').length,
        marginViolations: issues.filter((i) => i.type === 'margin').length,
      },
    };
  }
}

module.exports = { PdfLayoutValidator };
