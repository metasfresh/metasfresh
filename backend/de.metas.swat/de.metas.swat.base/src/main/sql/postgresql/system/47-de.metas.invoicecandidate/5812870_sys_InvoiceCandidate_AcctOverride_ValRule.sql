-- F01010.4 "Invoice Accounting Overrides"
-- Scope the C_ElementValue_Override_ID account picker to the account element (ElementType='AC')
-- of the accounting schema resolved for the record's org: getC_AcctSchema_ID(client, org).
-- Excludes summary accounts. Applied to BOTH the invoice-candidate override column (592836)
-- and the invoice-line override column (592837); the WebUI fields inherit the column-level rule.
--
-- AD_Val_Rule_ID 540792  (from ID server)

-- ============================================================
-- AD_Val_Rule
-- ============================================================
INSERT INTO AD_Val_Rule
    (AD_Val_Rule_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, Type, EntityType, Code)
VALUES
    (540792 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-09 10:00:00','YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC', 100,
     TO_TIMESTAMP('2026-07-09 10:00:00','YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC', 100,
     'C_ElementValue Account of Org-resolved AcctSchema',
     'S', 'de.metas.invoicecandidate',
     'C_ElementValue.C_Element_ID = (SELECT ase.C_Element_ID FROM C_AcctSchema_Element ase WHERE ase.C_AcctSchema_ID = getC_AcctSchema_ID(@#AD_Client_ID@, @AD_Org_ID@) AND ase.ElementType=''AC'' AND ase.IsActive=''Y'') AND C_ElementValue.IsActive=''Y'' AND C_ElementValue.IsSummary=''N''')
;

-- ============================================================
-- Point both override columns at the new rule
-- ============================================================
UPDATE AD_Column
   SET AD_Val_Rule_ID = 540792,
       Updated = TO_TIMESTAMP('2026-07-09 10:00:01','YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC',
       UpdatedBy = 100
 WHERE AD_Column_ID = 592836
;

UPDATE AD_Column
   SET AD_Val_Rule_ID = 540792,
       Updated = TO_TIMESTAMP('2026-07-09 10:00:02','YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC',
       UpdatedBy = 100
 WHERE AD_Column_ID = 592837
;
