CREATE OR REPLACE VIEW report.Direct_Costing_ElementValue_selection AS
SELECT
    e1.C_ElementValue_ID as lvl1_C_ElementValue_ID, e1.value as lvl1_value, e1.name as lvl1_name, ( e1.value || ' ' || e1.name ) as lvl1_label,
    e2.C_ElementValue_ID as lvl2_C_ElementValue_ID, e2.value as lvl2_value, e2.name as lvl2_name, ( e2.value || ' ' || e2.name ) as lvl2_label,
    e3.C_ElementValue_ID as lvl3_C_ElementValue_ID, e3.value as lvl3_value, e3.name as lvl3_name, ( e3.value || ' ' || e3.name ) as lvl3_label,

    e3.value IN (
                 '3000','3001',
                 '3900','3901','3902',
                 '4000','4001','4002','4003','4010','4011','4012','4013',
                 '4900'
        ) AS Margin1,

    e3.value IN (
                 '3300','3301',
                 '4300','4301','4302','4303','4310','4311','4312','4313',
                 '5000','5001','5002','5003','5004','5005','5700',
                 '5710',	'5720','5730','5740','5750'
        ) AS Margin2,

    e3.value IN (
                 '3402','3500','3501',
                 '4400','4401','4402','4410',
                 '4500','4501','4502','4510','4520',
                 '4700','4701','4702','4710','4711',
                 '5000','5001','5002','5003','5004','5005',
                 '5700','5710','5720','5730','5740','5750',
                 '5804','5808',
                 '6000',
                 '6200','6201','6202','6203','6204','6205','6206','6207','6208','6209','6210',
                 '6400','6401','6402','6403','6404','6405'
        ) AS Margin3,

    e3.value IN (
                 '3403','3404','3405','3407','3406','3408',
                 '3600',
                 '5000','5001','5002','5003','5004','5005','5020',
                 '5700','5710','5720','5730','5740','5750',
                 '5800','5801','5802','5803','5805','5806','5807',
                 '6001','6002','6003',
                 '6100','6101','6102','6103','6104','6105','6106','6107','6108','6109','6110','6111','6115',
                 '6300','6301',
                 '6406','6500','6501','6502','6503','6504','6505','6506','6507','6508','6509','6510','6511','6512','6513','6514','6515','6516',
                 '6600','6601','6602','6603',
                 '6700','6701',
                 '7500','7501', '7505', '7506',
                 '8015'
        ) AS Margin4,

    e3.value IN (
                 '6800','6801','6802','6803','6804','6805','6806','6807',
                 '6900','6901','6902','6903','6904','6905','6951','6954','6980',
                 '7900','7901','7902',
                 '8000','8010','8011','8012','8013','8014','8100','8101','8900'
        ) AS Margin5


FROM
    C_ElementValue e3
        JOIN (
        SELECT	evp.C_ElementValue_ID, evp.value, evp.name, ev.C_ElementValue_ID AS Source_ElementValue_ID
        FROM	C_ElementValue ev
                    JOIN C_Element e ON e.C_Element_ID = ev.C_Element_ID
                    JOIN AD_TreeNode tn ON tn.AD_Tree_ID = e.AD_Tree_ID AND tn.Node_ID = ev.C_ElementValue_ID
                    JOIN C_ElementValue evp ON evp.C_ElementValue_ID = tn.Parent_ID
    ) e2 ON e3.C_ElementValue_ID = e2.Source_ElementValue_ID

        LEFT JOIN (
        SELECT	evp.C_ElementValue_ID, evp.value, evp.name, ev.C_ElementValue_ID AS Source_ElementValue_ID
        FROM	C_ElementValue ev
                    JOIN C_Element e ON e.C_Element_ID = ev.C_Element_ID
                    JOIN AD_TreeNode tn ON tn.AD_Tree_ID = e.AD_Tree_ID AND tn.Node_ID = ev.C_ElementValue_ID
                    JOIN C_ElementValue evp ON evp.C_ElementValue_ID = tn.Parent_ID
    ) e1 ON e2.C_ElementValue_ID = e1.Source_ElementValue_ID
;



-- recreate data for Margin_Conf_Acct

create table backup.Margin_Conf_Acct_gh1579 as select * from report.Margin_Conf_Acct;

DELETE FROM report.Margin_Conf_Acct;
ALTER SEQUENCE report.Margin_Conf_Acct_Seq RESTART;
-- Insert Level 1
INSERT INTO report.Margin_Conf_Acct
(
    Margin_Conf_ID,
    Margin_Conf_Acct_ID,
    C_ElementValue_ID,
    lvl1_SeqNo, lvl2_SeqNo, lvl3_SeqNo, level, value, name,
    Use_activity_1000, Use_activity_2000, Use_activity_100, Use_activity_150
)
    (
        SELECT
            Margin_Conf_ID,
            NextVal('report.Margin_Conf_Acct_Seq'),
            lvl1_C_ElementValue_ID,
            rank() OVER (PARTITION BY Margin_Conf_ID ORDER BY lvl1_Value) * 10,
            0, 0,
            1, lvl1_value, lvl1_name, true, true, true, true
        FROM
            (
                SELECT DISTINCT lvl1_C_ElementValue_ID, lvl1_value, lvl1_name, 1000000 AS Margin_Conf_ID
                FROM report.Direct_Costing_ElementValue_selection
                WHERE Margin1
                UNION
                SELECT DISTINCT lvl1_C_ElementValue_ID, lvl1_value, lvl1_name, 1000001 AS Margin_Conf_ID
                FROM report.Direct_Costing_ElementValue_selection
                WHERE Margin2
                UNION
                SELECT DISTINCT lvl1_C_ElementValue_ID, lvl1_value, lvl1_name, 1000002 AS Margin_Conf_ID
                FROM report.Direct_Costing_ElementValue_selection
                WHERE Margin3
                UNION
                SELECT DISTINCT lvl1_C_ElementValue_ID, lvl1_value, lvl1_name, 1000003 AS Margin_Conf_ID
                FROM report.Direct_Costing_ElementValue_selection
                WHERE Margin4
                UNION
                SELECT DISTINCT lvl1_C_ElementValue_ID, lvl1_value, lvl1_name, 1000004 AS Margin_Conf_ID
                FROM report.Direct_Costing_ElementValue_selection
                WHERE Margin5
            ) a
        ORDER BY
            Margin_Conf_ID
    )
;

-- Insert Level 2
INSERT INTO report.Margin_Conf_Acct
(
    Margin_Conf_ID,
    Margin_Conf_Acct_ID,
    Parent_Account_ID,
    C_ElementValue_ID, lvl1_SeqNo, lvl2_SeqNo, lvl3_SeqNo, level, value, name,
    Use_activity_1000, Use_activity_2000, Use_activity_100, Use_activity_150
)
    (
        SELECT
            a.Margin_Conf_ID,
            NextVal('report.Margin_Conf_Acct_Seq'),
            b.Margin_Conf_Acct_ID,
            lvl2_C_ElementValue_ID,
            b.lvl1_SeqNo,
            rank() OVER (PARTITION BY a.Margin_Conf_ID ORDER BY lvl2_Value) * 10,
            0,
            2, a.lvl2_value, a.lvl2_name, true, true, true, true
        FROM
            (
                SELECT DISTINCT lvl2_C_ElementValue_ID, lvl1_value, lvl2_value, lvl2_name, 1000000 AS Margin_Conf_ID
                FROM report.Direct_Costing_ElementValue_selection
                WHERE Margin1
                UNION
                SELECT DISTINCT lvl2_C_ElementValue_ID, lvl1_value, lvl2_value, lvl2_name, 1000001 AS Margin_Conf_ID
                FROM report.Direct_Costing_ElementValue_selection
                WHERE Margin2
                UNION
                SELECT DISTINCT lvl2_C_ElementValue_ID, lvl1_value, lvl2_value, lvl2_name, 1000002 AS Margin_Conf_ID
                FROM report.Direct_Costing_ElementValue_selection
                WHERE Margin3
                UNION
                SELECT DISTINCT lvl2_C_ElementValue_ID, lvl1_value, lvl2_value, lvl2_name, 1000003 AS Margin_Conf_ID
                FROM report.Direct_Costing_ElementValue_selection
                WHERE Margin4
                UNION
                SELECT DISTINCT lvl2_C_ElementValue_ID, lvl1_value, lvl2_value, lvl2_name, 1000004 AS Margin_Conf_ID
                FROM report.Direct_Costing_ElementValue_selection
                WHERE Margin5
                ORDER BY
                    Margin_Conf_ID
            ) a
                JOIN report.Margin_Conf_Acct b
                     ON a.Margin_Conf_ID = b.Margin_Conf_ID
                         AND a.lvl1_value = b.value
                         AND level = 1
        ORDER BY
            a.Margin_Conf_ID,
            b.Margin_Conf_Acct_ID
    )
;

-- Insert Level 3
INSERT INTO report.Margin_Conf_Acct
(
    Margin_Conf_ID,
    Margin_Conf_Acct_ID,
    Parent_Account_ID,
    C_ElementValue_ID, lvl1_SeqNo, lvl2_SeqNo, lvl3_SeqNo, level, value, name,
    Use_activity_1000, Use_activity_2000, Use_activity_100, Use_activity_150
)
    (
        SELECT
            a.Margin_Conf_ID,
            NextVal('report.Margin_Conf_Acct_Seq'),
            b.Margin_Conf_Acct_ID,
            lvl3_C_ElementValue_ID,
            b.lvl1_SeqNo, b.lvl2_SeqNo,
            rank() OVER (PARTITION BY a.Margin_Conf_ID ORDER BY lvl3_Value) * 10,
            3, a.lvl3_value, a.lvl3_name,
            --  für die Konten 50xx und 57xx soll in Deckungsbeitrag 3 und 4 Die Kostenstelle 1000 ignoriert werden
            CASE WHEN lvl2_Value IN ('500', '570') AND a.Margin_Conf_ID IN (1000002, 1000003) THEN false ELSE true END,
            --  für die Konten 50xx und 57xx soll in Deckungsbeitrag 3 und 4 Die Kostenstelle 2000 ignoriert werden
            CASE WHEN lvl2_Value IN ('500', '570') AND a.Margin_Conf_ID IN (1000002, 1000003) THEN false ELSE true END,
            --  für die Konten 50xx und 57xx soll in Deckungsbeitrag 2 und 4 Die Kostenstelle 100 ignoriert werden
            CASE WHEN lvl2_Value IN ('500', '570') AND a.Margin_Conf_ID IN (1000001, 1000003) THEN false ELSE true END,
            --  für die Konten 50xx und 57xx soll in Deckungsbeitrag 2 und 3 Die Kostenstelle 150 ignoriert werden
            CASE WHEN lvl2_Value IN ('500', '570') AND a.Margin_Conf_ID IN (1000001, 1000002) THEN false ELSE true END
        FROM
            (
                SELECT DISTINCT lvl3_C_ElementValue_ID, lvl2_value, lvl3_value, lvl3_name, 1000000 AS Margin_Conf_ID
                FROM report.Direct_Costing_ElementValue_selection
                WHERE Margin1
                UNION
                SELECT DISTINCT lvl3_C_ElementValue_ID, lvl2_value, lvl3_value, lvl3_name, 1000001 AS Margin_Conf_ID
                FROM report.Direct_Costing_ElementValue_selection
                WHERE Margin2
                UNION
                SELECT DISTINCT lvl3_C_ElementValue_ID, lvl2_value, lvl3_value, lvl3_name, 1000002 AS Margin_Conf_ID
                FROM report.Direct_Costing_ElementValue_selection
                WHERE Margin3
                UNION
                SELECT DISTINCT lvl3_C_ElementValue_ID, lvl2_value, lvl3_value, lvl3_name, 1000003 AS Margin_Conf_ID
                FROM report.Direct_Costing_ElementValue_selection
                WHERE Margin4
                UNION
                SELECT DISTINCT lvl3_C_ElementValue_ID, lvl2_value, lvl3_value, lvl3_name, 1000004 AS Margin_Conf_ID
                FROM report.Direct_Costing_ElementValue_selection
                WHERE Margin5
                ORDER BY
                    Margin_Conf_ID
            ) a
                JOIN report.Margin_Conf_Acct b
                     ON a.Margin_Conf_ID = b.Margin_Conf_ID
                         AND a.lvl2_value = b.value
                         AND level = 2
    )
;
