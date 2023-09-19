-- Add the IsTaxIncluded to the QuickInput
UPDATE ad_sysconfig
SET value     = 'PostingSign,GL_Account_ID,Amount,M_SectionCode_ID,IsTaxIncluded?,C_Tax_ID?'
  , updated   = NOW()
  , updatedby = 99
WHERE name = 'SAP_GLJournalLine.quickInput.layout'
;
