import { useEffect, useRef } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';

export const useLocationChange = (onChange) => {
  const currentRoute = useRouteMatch();
  const history = useHistory();

  const onChangeRef = useRef(onChange);
  onChangeRef.current = onChange;
  // Stale by one navigation for a caller that never remounts: our listener runs before Router
  // propagates the new RouteContext. Derive the route from currentLocation if you need it exact.
  const currentRouteRef = useRef(currentRoute);
  currentRouteRef.current = currentRoute;

  const trackLocation = () => {
    const currentLocation = window.location.href;
    const lastKnownLocation = sessionStorage.getItem('lastKnownLocation');

    if (currentLocation !== lastKnownLocation) {
      sessionStorage.setItem('lastKnownLocation', currentLocation);
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
