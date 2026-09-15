/**
 * Widget Utilities for metasfresh E2E Playwright Tests
 *
 * This module provides reusable utility functions for interacting with all
 * metasfresh widget types. These utilities are language-independent and use
 * stable selectors based on database field names.
 *
 * Widget Types Supported:
 * - Text/LongText: Text inputs and text areas
 * - Numeric: Integer, Number, Amount, Quantity
 * - Date/Time: Date, DateTime, Time, ZonedDateTime, Timestamp
 * - Boolean: Switch, YesNo (checkbox)
 * - Lookup: Dropdown with search (typeahead)
 * - List: Dropdown with predefined options
 * - Address: Location/Address editor
 * - Image: Binary data upload
 * - ProductAttributes: Attribute set instance editor
 * - Button: Process/Action buttons
 *
 * Usage:
 *   import { LookupWidget, TextWidget, DateWidget } from '../utils/widgets';
 *
 *   // Set a lookup field
 *   await LookupWidget.setValue('C_BPartner_ID', 'Customer Name');
 *
 *   // Set a text field
 *   await TextWidget.setValue('Name', 'Test Record');
 *
 *   // Get a date field value
 *   const date = await DateWidget.getValue('T_Date');
 *
 * @module widgets
 */

// Core widget utilities
export { TextWidget } from './TextWidget';
export { NumericWidget } from './NumericWidget';
export { DateWidget } from './DateWidget';
export { BooleanWidget } from './BooleanWidget';
export { LookupWidget } from './LookupWidget';
export { ListWidget } from './ListWidget';

// Special widget utilities
export { AddressWidget } from './AddressWidget';
export { ImageWidget } from './ImageWidget';
export { AttributesWidget } from './AttributesWidget';
export { ButtonWidget } from './ButtonWidget';

// Common utilities
export { WidgetCommon } from './WidgetCommon';
