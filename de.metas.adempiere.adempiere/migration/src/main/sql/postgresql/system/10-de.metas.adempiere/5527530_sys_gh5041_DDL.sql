-- 2019-07-18T06:59:03.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- /* DDL */ CREATE TABLE public.C_Payment_Reservation_Capture (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Amount NUMERIC NOT NULL, C_Payment_Reservation_Capture_ID NUMERIC(10) NOT NULL, C_Payment_Reservation_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Status VARCHAR(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_Payment_Reservation_Capture_Key PRIMARY KEY (C_Payment_Reservation_Capture_ID), CONSTRAINT CPaymentReservation_CPaymentReservationCapture FOREIGN KEY (C_Payment_Reservation_ID) REFERENCES public.C_Payment_Reservation DEFERRABLE INITIALLY DEFERRED)
-- ;

