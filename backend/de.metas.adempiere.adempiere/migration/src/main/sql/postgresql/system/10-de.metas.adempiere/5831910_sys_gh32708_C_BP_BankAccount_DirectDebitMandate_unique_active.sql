-- SEPA direct debit: one active mandate per bank account and organisation.
-- A mandate of org 0 and a mandate of another org on the same bank account have different AD_Org_ID, so both are allowed.
-- ID server allocations: script 5831910, AD_Index_Table 540877, AD_Index_Column 541555 / 541556

-- 1. Guard: stop with a readable list if the data already violates the rule (the unique index below would fail with a bare duplicate-key error)
DO
$$
    DECLARE
        v_duplicates text;
    BEGIN
        SELECT string_agg('MandateReference=' || m.MandateReference
                              || ', BPartner=' || COALESCE(bp.Value, '?')
                              || ', IBAN=' || COALESCE(ba.IBAN, '?')
                              || ', C_BP_BankAccount_ID=' || m.C_BP_BankAccount_ID
                              || ', AD_Org_ID=' || m.AD_Org_ID,
                          E'\n' ORDER BY m.C_BP_BankAccount_ID, m.AD_Org_ID, m.MandateReference)
        INTO v_duplicates
        FROM C_BP_BankAccount_DirectDebitMandate m
                 LEFT JOIN C_BPartner bp ON bp.C_BPartner_ID = m.C_BPartner_ID
                 LEFT JOIN C_BP_BankAccount ba ON ba.C_BP_BankAccount_ID = m.C_BP_BankAccount_ID
        WHERE m.IsActive = 'Y'
          AND EXISTS (SELECT 1
                      FROM C_BP_BankAccount_DirectDebitMandate o
                      WHERE o.IsActive = 'Y'
                        AND o.C_BP_BankAccount_ID = m.C_BP_BankAccount_ID
                        AND o.AD_Org_ID = m.AD_Org_ID
                        AND o.C_BP_BankAccount_DirectDebitMandate_ID <> m.C_BP_BankAccount_DirectDebitMandate_ID);

        IF v_duplicates IS NOT NULL THEN
            RAISE EXCEPTION E'More than one active direct debit mandate per bank account and organisation:\n%\nHint: deactivate all but one active mandate per bank account and organisation, then re-run the migration', v_duplicates;
        END IF;
    END
$$;

-- 2. Dictionary: index with its user friendly error message
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Updated,UpdatedBy,WhereClause)
VALUES (0,540877 /*From ID Server*/,0,542522,TO_TIMESTAMP('2026-10-28 11:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Pro Bankkonto und Organisation ist nur ein aktives Lastschriftmandat erlaubt.','Y','Y','C_BP_BankAccount_DirectDebitMandate_UQ_Active',TO_TIMESTAMP('2026-10-28 11:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'IsActive=''Y''');

INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID,ErrorMsg,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Index_Table_ID,t.ErrorMsg,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Index_Table t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540877
  AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID);

UPDATE AD_Index_Table_Trl SET ErrorMsg='Only one active direct debit mandate per bank account and organisation is allowed.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-28 11:00:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Index_Table_ID=540877 AND AD_Language='en_US';

UPDATE AD_Index_Table_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-28 11:00:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Index_Table_ID=540877 AND AD_Language IN ('de_DE','de_CH');

INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy)
VALUES (0,590828,541555 /*From ID Server*/,540877,0,TO_TIMESTAMP('2026-10-28 11:00:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',10,TO_TIMESTAMP('2026-10-28 11:00:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100);

INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy)
VALUES (0,590820,541556 /*From ID Server*/,540877,0,TO_TIMESTAMP('2026-10-28 11:00:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',20,TO_TIMESTAMP('2026-10-28 11:00:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100);

-- 3. DDL: the index name must equal AD_Index_Table.Name
CREATE UNIQUE INDEX C_BP_BankAccount_DirectDebitMandate_UQ_Active
    ON C_BP_BankAccount_DirectDebitMandate (C_BP_BankAccount_ID, AD_Org_ID)
    WHERE IsActive = 'Y';
