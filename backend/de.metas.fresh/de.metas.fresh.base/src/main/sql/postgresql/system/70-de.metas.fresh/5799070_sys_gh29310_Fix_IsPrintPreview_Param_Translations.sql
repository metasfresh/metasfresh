-- The SWING_CLIENT-recorded migration 5797620 hardcoded the de_DE base-language values
-- ('Druckvorschau' / 'Generieren Sie PDFs ohne zu drucken.') into AD_Process_Para (base)
-- and copied them into every AD_Process_Para_Trl row with IsTranslated='N'. As a result
-- English users saw German labels on the new IsPrintPreview parameter of
-- M_HU_MultipleSelection_Report_Print_Label (AD_Process 585354).
--
-- AD_Element 582572 (IsPrintPreview) is already correctly translated:
--   - base (English): Name='Print Preview', Description/Help='Generate PDF without printing.'
--   - de_DE: Name='Druckvorschau', Description/Help='Generieren Sie PDFs ohne zu drucken.', IsTranslated='Y'
--   - de_CH: same as de_DE, IsTranslated='Y'
--
-- Run the propagation function to overwrite the AD_Process_Para and _Trl rows from the
-- element so all languages render correctly and IsTranslated reflects the element's state.

SELECT update_Process_Para_Translation_From_AD_Element(582572);
