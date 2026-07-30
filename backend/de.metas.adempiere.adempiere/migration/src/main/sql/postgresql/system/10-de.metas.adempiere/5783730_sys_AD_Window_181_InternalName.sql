-- Set InternalName for Purchase Order window (AD_Window_ID=181)
-- Previously was '181 (Todo: Set Internal Name for UI testing)'
-- Used by E2E tests for stable, language-independent window identification

UPDATE AD_Window
SET InternalName = 'C_Order_Purchase'
WHERE AD_Window_ID = 181
  AND (InternalName IS NULL OR InternalName LIKE '%Todo%');
