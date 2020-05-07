DROP FUNCTION IF EXISTS geographical_distance(real, real, real, real);

CREATE FUNCTION geographical_distance(lat1 real, lon1 real, lat2 real, lon2 real) RETURNS real
    LANGUAGE plpgsql IMMUTABLE
AS
$$
DECLARE
    earth_radius_km CONSTANT real := 6378.1;
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
