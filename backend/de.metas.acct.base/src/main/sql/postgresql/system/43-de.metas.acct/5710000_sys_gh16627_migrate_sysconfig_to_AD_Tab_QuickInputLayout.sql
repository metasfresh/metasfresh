UPDATE ad_tab
SET quickinputlayout = (SELECT value FROM ad_sysconfig WHERE name = 'SAP_GLJournalLine.quickInput.layout')
WHERE ad_table_id = get_table_id('SAP_GLJournalLine')
;

DELETE FROM ad_sysconfig
WHERE name = 'SAP_GLJournalLine.quickInput.layout'
;
