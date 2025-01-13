import { useEffect } from 'react';

export const useLocationChange = (onChange) => {
  const trackLocation = () => {
    const currentLocation = window.location.href;
    const lastKnownLocation = sessionStorage.getItem('lastKnownLocation');
    // console.log('trackLocation', { currentLocation, lastKnownLocation });

    if (currentLocation !== lastKnownLocation) {
      sessionStorage.setItem('lastKnownLocation', currentLocation);
      onChange({
        currentLocation,
        prevLocation: lastKnownLocation,
      });
    }
  };

  useEffect(() => {
    window.addEventListener('popstate', trackLocation);
    window.addEventListener('hashchange', trackLocation);

    trackLocation();

    return () => {
      window.removeEventListener('popstate', trackLocation);
      window.removeEventListener('hashchange', trackLocation);
    };
  }, []);
};
