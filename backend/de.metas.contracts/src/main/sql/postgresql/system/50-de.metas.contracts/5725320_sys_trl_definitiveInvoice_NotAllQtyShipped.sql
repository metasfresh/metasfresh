-- Run mode: SWING_CLIENT

-- Value: de.metas.contracts.definitiveinvoice.process.NotAllQtyShipped
-- 2024-06-03T09:33:04.087Z
UPDATE AD_Message SET MsgText='Nicht die gesamte eingegangene Menge für Vertrag {} wurde versandt. Die verbleibende Menge muss entweder verkauft und versandt oder durch Inventur entsorgt werden.', MsgType='E', Value='de.metas.contracts.definitiveinvoice.process.NotAllQtyShipped',Updated=TO_TIMESTAMP('2024-06-03 12:33:04.085','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=101
;

-- 2024-06-03T09:33:04.093Z
UPDATE AD_Message_Trl trl SET MsgText='Nicht die gesamte eingegangene Menge für Vertrag {} wurde versandt. Die verbleibende Menge muss entweder verkauft und versandt oder durch Inventur entsorgt werden.' WHERE AD_Message_ID=101 AND AD_Language='de_DE'
;

-- Value: de.metas.contracts.definitiveinvoice.process.NotAllQtyShipped
-- 2024-06-03T09:51:17.439Z
UPDATE AD_Message_Trl SET MsgText='Definitive Schlussabrechnung kann nicht erstellt werden, da nicht die gesamte Eingangsmenge für Vertrag {} versandt wurde. Die verbleibende Menge muss entweder verkauft und versandt oder durch Inventur entsorgt werden.',Updated=TO_TIMESTAMP('2024-06-03 12:51:17.439','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=101
;

-- Value: de.metas.contracts.definitiveinvoice.process.NotAllQtyShipped
-- 2024-06-03T09:51:27.500Z
UPDATE AD_Message_Trl SET MsgText='Definitive invoice cannot be created because not all received quantity for contract {} has been shipped. Remaining quantity must be either sold and shipped, or disposed of via physical inventory.',Updated=TO_TIMESTAMP('2024-06-03 12:51:27.5','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Message_ID=101
;

-- Value: de.metas.contracts.definitiveinvoice.process.NotAllQtyShipped
-- 2024-06-03T09:51:29.499Z
UPDATE AD_Message_Trl SET MsgText='Definitive invoice cannot be created because not all received quantity for contract {} has been shipped. Remaining quantity must be either sold and shipped, or disposed of via physical inventory.',Updated=TO_TIMESTAMP('2024-06-03 12:51:29.499','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=101
;

-- Value: de.metas.contracts.definitiveinvoice.process.NotAllQtyShipped
-- 2024-06-03T09:53:42.507Z
UPDATE AD_Message_Trl SET MsgText='Definitive Schlusszahlung kann nicht erstellt werden, da nicht die gesamte Eingangsmenge für Vertrag {} versandt wurde. Die verbleibende Menge muss entweder verkauft und versandt oder durch Inventur entsorgt werden.',Updated=TO_TIMESTAMP('2024-06-03 12:53:42.507','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=101
;

-- Value: de.metas.contracts.definitiveinvoice.process.NotAllQtyShipped
-- 2024-06-03T09:53:48.583Z
UPDATE AD_Message_Trl SET MsgText='Definitive Schlusszahlung kann nicht erstellt werden, da nicht die gesamte Eingangsmenge für Vertrag {} versandt wurde. Die verbleibende Menge muss entweder verkauft und versandt oder durch Inventur entsorgt werden.',Updated=TO_TIMESTAMP('2024-06-03 12:53:48.583','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=101
;

-- 2024-06-03T09:53:48.584Z
UPDATE AD_Message SET MsgText='Definitive Schlusszahlung kann nicht erstellt werden, da nicht die gesamte Eingangsmenge für Vertrag {} versandt wurde. Die verbleibende Menge muss entweder verkauft und versandt oder durch Inventur entsorgt werden.' WHERE AD_Message_ID=101
;

