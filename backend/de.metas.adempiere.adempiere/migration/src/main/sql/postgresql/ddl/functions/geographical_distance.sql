DROP FUNCTION IF EXISTS geographical_distance(numeric, numeric, numeric, numeric);

CREATE FUNCTION geographical_distance(lat1 numeric, lon1 numeric, lat2 numeric, lon2 numeric) RETURNS numeric
    LANGUAGE plpgsql IMMUTABLE
AS
$$
DECLARE
    earth_radius_km CONSTANT numeric := 6378.1;
BEGIN
    RETURN earth_radius_km * acos(
                    cos(radians(lat1)) *
                    cos(radians(lat2)) *
                    cos(
                                radians(lon1) - radians(lon2)
                        ) +
                    sin(radians(lat1)) *
                    sin(radians(lat2))
        );
END;
$$;

