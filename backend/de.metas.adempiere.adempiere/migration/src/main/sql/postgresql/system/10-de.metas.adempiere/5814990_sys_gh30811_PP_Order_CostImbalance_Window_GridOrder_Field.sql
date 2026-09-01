-- Both grid orderings are live on this tab: the WebUI reads AD_UI_Element.SeqNoGrid, the Swing client
-- reads AD_Field.SeqNoGrid. Mirror CostDifference's grid position onto the AD_Field layer.

UPDATE AD_Field SET SeqNoGrid=35,
       Updated=TO_TIMESTAMP('2026-07-21 00:40:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=781753
;
