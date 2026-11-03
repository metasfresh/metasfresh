update M_Picking_Job_Step_PickedHU p
set Picked_RenderedQRCode=
    (select qr.renderedqrcode
     from M_HU_QRCode qr
         inner join m_hu_qrcode_assignment assignment on qr.m_hu_qrcode_id = assignment.m_hu_qrcode_id
     where assignment.m_hu_id = p.picked_hu_id
     limit 1)
where Picked_RenderedQRCode is null;
