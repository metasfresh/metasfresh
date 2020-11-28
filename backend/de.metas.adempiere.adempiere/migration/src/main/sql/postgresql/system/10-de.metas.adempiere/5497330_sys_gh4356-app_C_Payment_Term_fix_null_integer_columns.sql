
ALTER TABLE public.c_paymentterm ALTER COLUMN fixmonthcutoff SET DEFAULT 0;
UPDATE public.c_paymentterm SET fixmonthcutoff=0 WHERE fixmonthcutoff IS NULL;
ALTER TABLE public.c_paymentterm ALTER COLUMN fixmonthcutoff SET NOT NULL;

ALTER TABLE public.c_paymentterm ALTER COLUMN fixmonthday SET DEFAULT 0;
UPDATE public.c_paymentterm SET fixmonthday=0 WHERE fixmonthday IS NULL;
ALTER TABLE public.c_paymentterm ALTER COLUMN fixmonthday SET NOT NULL;

ALTER TABLE public.c_paymentterm ALTER COLUMN fixmonthoffset SET DEFAULT 0;
UPDATE public.c_paymentterm SET fixmonthoffset=0 WHERE fixmonthoffset IS NULL;
ALTER TABLE public.c_paymentterm ALTER COLUMN fixmonthoffset SET NOT NULL;

