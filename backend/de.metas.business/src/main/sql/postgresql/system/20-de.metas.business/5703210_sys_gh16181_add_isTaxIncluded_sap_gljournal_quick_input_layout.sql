-- Add the IsTaxIncluded to the QuickInput
UPDATE ad_sysconfig
SET value     = 'PostingSign,GL_Account_ID,Amount,M_SectionCode_ID,C_Tax_ID?,IsTaxIncluded?'
  , updated   = NOW()
  , updatedby = 99
WHERE name = 'SAP_GLJournalLine.quickInput.layout'
;
