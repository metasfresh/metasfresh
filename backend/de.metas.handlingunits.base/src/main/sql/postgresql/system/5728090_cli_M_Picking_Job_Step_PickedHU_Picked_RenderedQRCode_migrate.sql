UPDATE M_Picking_Job_Step_PickedHU p
SET Picked_RenderedQRCode=(SELECT qr.renderedqrcode
                           FROM M_HU_QRCode_Assignment a
                                    INNER JOIN M_HU_QRCode qr ON qr.m_hu_qrcode_id = a.m_hu_qrcode_id
                           WHERE a.m_hu_id = p.picked_hu_id)
WHERE Picked_RenderedQRCode IS NULL
;
