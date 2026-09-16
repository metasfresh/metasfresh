import { useEffect, useRef } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';

export const useLocationChange = (onChange) => {
  const currentRoute = useRouteMatch();
  const history = useHistory();

  // The subscription below is installed once, so it must not close over the first render's props.
  // Refs keep the callback and the matched route current without re-subscribing on every render.
  //
  // CAVEAT on currentRoute: this hook's history.listen subscription is registered when its owner
  // mounts, which is before Router's own subscription further up the tree, so it runs before Router
  // has propagated a fresh RouteContext. For a caller that never remounts, currentRouteRef therefore
  // still holds the PREVIOUS match when the callback fires - currentLocation is correct, currentRoute
  // is one navigation behind. Nothing is affected today, but not because the field is unused:
  // useUITraceLocationChange DOES read currentRoute.params.applicationId - its callback simply never
  // executes, because ScreenToaster's child-level effect always claims the shared lastKnownLocation
  // key first. Whoever fixes that will start hitting this immediately, so derive the route from the
  // reported location (matchPath) rather than trusting this field.
  const onChangeRef = useRef(onChange);
  onChangeRef.current = onChange;
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
    // history.push/replace do NOT emit a native popstate event — that fires only for genuine browser
    // back/forward. Without the router subscription this hook depended on its host component
    // REMOUNTING on each navigation, so that the trackLocation() call below re-ran; a caller mounted
    // once for the app's lifetime (ScreenToaster, which dismisses toasts on navigation) would then
    // never fire again, and a toast raised on one screen would follow the operator to the next.
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
