import { useEffect, useRef } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';

const LAST_KNOWN_LOCATION_KEY = 'lastKnownLocation';

export const useLocationChange = (onChange) => {
  const currentRoute = useRouteMatch();
  const history = useHistory();

  const onChangeRef = useRef(onChange);
  onChangeRef.current = onChange;

  // Per-instance, and seeded once: sessionStorage is shared by every consumer of this hook, so
  // comparing against it directly lets whichever consumer runs first swallow the change for all
  // the others. The stored value is only the seed, which is what keeps a consumer mounting
  // mid-session (or after a page reload) from re-announcing a location that is already current.
  const lastSeenLocationRef = useRef(undefined);
  if (lastSeenLocationRef.current === undefined) {
    lastSeenLocationRef.current = sessionStorage.getItem(LAST_KNOWN_LOCATION_KEY);
  }

  // Stale by one navigation for a caller that never remounts: our listener runs before Router
  // propagates the new RouteContext. Derive the route from currentLocation if you need it exact.
  const currentRouteRef = useRef(currentRoute);
  currentRouteRef.current = currentRoute;

  const trackLocation = () => {
    const currentLocation = window.location.href;
    const lastKnownLocation = lastSeenLocationRef.current;

    if (currentLocation !== lastKnownLocation) {
      lastSeenLocationRef.current = currentLocation;
      sessionStorage.setItem(LAST_KNOWN_LOCATION_KEY, currentLocation);
      onChangeRef.current({
        currentLocation,
        currentRoute: currentRouteRef.current,
        prevLocation: lastKnownLocation,
      });
    }
  };

  useEffect(() => {
    // history.listen is what catches push/replace; popstate only fires on browser back/forward.
    // Without it the hook only worked because its caller remounted on every navigation.
    const unlisten = history.listen(trackLocation);
    window.addEventListener('popstate', trackLocation);
    window.addEventListener('hashchange', trackLocation);

    trackLocation();

    return () => {
      unlisten();
      window.removeEventListener('popstate', trackLocation);
      window.removeEventListener('hashchange', trackLocation);
    };
  }, []);
};
