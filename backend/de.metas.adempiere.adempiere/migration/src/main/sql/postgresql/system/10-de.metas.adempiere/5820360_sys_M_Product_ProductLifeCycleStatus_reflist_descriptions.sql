-- Product Life Cycle Status (BBS-Status) ref-list (AD_Reference 542123): give every value a Description.
-- The WebUI renders it as the dropdown tooltip, so this is where a user learns what a status blocks.
-- All four were NULL.
--
-- Base language is de_DE: German goes into AD_Ref_List.Description, English into the en_US Trl (same
-- split 5817300 made for Name vs ValueName). de_CH is translated too and gets the German text.
-- Wording mirrors de.metas.product.BBSStatus, the source of truth for the matrix.

-- O = OK ---------------------------------------------------------------------------------------------
UPDATE AD_Ref_List SET Description='Keine Einschränkung: Einkauf, Verkauf, Kommissionierung, Produktion und Versand sind erlaubt.',
    Updated=TO_TIMESTAMP('2026-08-26 08:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544324 /* O */
;

UPDATE AD_Ref_List_Trl SET Description='Keine Einschränkung: Einkauf, Verkauf, Kommissionierung, Produktion und Versand sind erlaubt.',
    Updated=TO_TIMESTAMP('2026-08-26 08:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544324 AND AD_Language IN ('de_DE','de_CH')
;

UPDATE AD_Ref_List_Trl SET Description='No restriction: purchasing, selling, picking, manufacturing and shipping are all allowed.',
    Updated=TO_TIMESTAMP('2026-08-26 08:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544324 AND AD_Language='en_US'
;

-- A = Auslauf / Phase-out ----------------------------------------------------------------------------
UPDATE AD_Ref_List SET Description='Kein Einkauf und keine Produktion mehr. Verkauf, Kommissionierung und Versand des Restbestands bleiben erlaubt.',
    Updated=TO_TIMESTAMP('2026-08-26 08:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544325 /* A */
;

UPDATE AD_Ref_List_Trl SET Description='Kein Einkauf und keine Produktion mehr. Verkauf, Kommissionierung und Versand des Restbestands bleiben erlaubt.',
    Updated=TO_TIMESTAMP('2026-08-26 08:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544325 AND AD_Language IN ('de_DE','de_CH')
;

UPDATE AD_Ref_List_Trl SET Description='No new purchasing and no new manufacturing. Selling, picking and shipping of the remaining stock stay allowed.',
    Updated=TO_TIMESTAMP('2026-08-26 08:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544325 AND AD_Language='en_US'
;

-- G = Gesperrt / Blocked -----------------------------------------------------------------------------
UPDATE AD_Ref_List SET Description='Vollständig gesperrt: Einkauf, Verkauf, Kommissionierung, Produktion und Versand sind nicht erlaubt.',
    Updated=TO_TIMESTAMP('2026-08-26 08:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544326 /* G */
;

UPDATE AD_Ref_List_Trl SET Description='Vollständig gesperrt: Einkauf, Verkauf, Kommissionierung, Produktion und Versand sind nicht erlaubt.',
    Updated=TO_TIMESTAMP('2026-08-26 08:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544326 AND AD_Language IN ('de_DE','de_CH')
;

UPDATE AD_Ref_List_Trl SET Description='Fully blocked: purchasing, selling, picking, manufacturing and shipping are all refused.',
    Updated=TO_TIMESTAMP('2026-08-26 08:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544326 AND AD_Language='en_US'
;

-- N = Lieferstopp / Delivery stop --------------------------------------------------------------------
UPDATE AD_Ref_List SET Description='Ware darf das Lager nicht verlassen: Kommissionierung und Versand sind gesperrt. Einkauf, Verkauf und Produktion bleiben erlaubt.',
    Updated=TO_TIMESTAMP('2026-08-26 08:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544327 /* N */
;

UPDATE AD_Ref_List_Trl SET Description='Ware darf das Lager nicht verlassen: Kommissionierung und Versand sind gesperrt. Einkauf, Verkauf und Produktion bleiben erlaubt.',
    Updated=TO_TIMESTAMP('2026-08-26 08:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544327 AND AD_Language IN ('de_DE','de_CH')
;

UPDATE AD_Ref_List_Trl SET Description='Goods must not leave the warehouse: picking and shipping are blocked. Purchasing, selling and manufacturing stay allowed.',
    Updated=TO_TIMESTAMP('2026-08-26 08:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544327 AND AD_Language='en_US'
;
