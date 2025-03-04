update M_Picking_Job_Step_PickedHU p set Picked_RenderedQRCode=(select qr.renderedqrcode from M_HU_QRCode qr where qr.m_hu_id=p.picked_hu_id)
where Picked_RenderedQRCode is null;
