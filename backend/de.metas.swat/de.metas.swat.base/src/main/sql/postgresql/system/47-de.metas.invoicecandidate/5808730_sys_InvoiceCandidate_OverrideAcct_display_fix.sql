-- me03 #30443 — F01010.4 — fix NT5 review findings on the resolved-override-account display column (592842):
--  (1) lowercase the ColumnSQL keywords + FROM-clause table name — the Oracle→PostgreSQL conversion
--      layer (Convert_PostgreSQL) inspects SQL case-sensitively; uppercase keywords in a subquery can
--      trigger top-level rewrites that corrupt generic PO loads across the WebUI. (5808720 shipped it
--      uppercase; 5808720 is already applied, so this is an append-only follow-up, not an edit.)
--  (2) call the orchestrator update_TRL_Tables_On_AD_Element_TRL_Update for the new element 585025
--      so its translations fully propagate to all dependent _Trl tables.

UPDATE AD_Column
SET    ColumnSQL = '(select ev.Value || '' - '' || ev.Name from c_elementvalue ev where ev.C_ElementValue_ID = C_Invoice_Candidate.C_ElementValue_Override_ID)',
       Updated   = TO_TIMESTAMP('2026-06-18 14:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Column_ID = 592842 /*From ID Server*/;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585025 /*From ID Server*/)
;
