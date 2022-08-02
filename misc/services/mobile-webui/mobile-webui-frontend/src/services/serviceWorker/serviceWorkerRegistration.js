// This optional code is used to register a service worker.
// register() is not called by default.

// This lets the app load faster on subsequent visits in production, and gives
// it offline capabilities. However, it also means that developers (and users)
// will only see deployed updates on subsequent visits to a page, after all the
// existing tabs open on the page have been closed, since previously cached
// resources are updated in the background.

// To learn more about the benefits of this model and instructions on how to
// opt-in, read https://cra.link/PWA

import { REGISTER_SERVICE_WORKER } from '../../constants';

const isLocalhost = Boolean(
  window.location.hostname === 'localhost' ||
    // [::1] is the IPv6 localhost address.
    window.location.hostname === '[::1]' ||
    // 127.0.0.0/8 are considered localhost for IPv4.
    window.location.hostname.match(/^127(?:\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$/)
);

export const setupServiceWorker = () => {
  //
  // Unregister previous service workers, and register current one
  if (navigator.serviceWorker) {
    navigator.serviceWorker
      .getRegistrations()
      .then(function (registrations) {
        for (let registration of registrations) {
          registration
            .unregister()
            .then(function () {
              if (self.clients) return self.clients.matchAll();
            })
            .then(function (clients) {
              if (clients) clients.forEach((client) => client.navigate(client.url));
            });
        }
        // once all remove register new one
        // If you want your app to work offline and load faster, you can change
        // unregister() to register() below. Note this comes with some pitfalls.
        // Learn more about service workers: https://cra.link/PWA
        register();
      })
      .catch(function (err) {
        console.log('Service Worker registration failed: ', err);
      });
  }

  window.addEventListener('beforeinstallprompt', (e) => {
    console.log('Install event triggered:', e);
    // e.preventDefault(); - this is going to disable the prompt if uncommented !
    // See if the app is already installed, in that case, do nothing
    if (window.matchMedia && window.matchMedia('(display-mode: standalone)').matches) {
      // Already installed
      return false;
    }
  });
};

function register(config) {
  console.log('[ServiceWorker] - Call register()');
  if ('serviceWorker' in navigator) {
    console.log('[ServiceWorker] - Found in navigator..');

    // direct loading of service worker - as it `load` below didn't work on prod
    const swUrl = `./service-worker.js`;
    if (isLocalhost) {
      // This is running on localhost. Let's check if a service worker still exists or not.
      checkValidServiceWorker(swUrl, config);

      // Add some additional logging to localhost, pointing developers to the
      // service worker/PWA documentation.
      navigator.serviceWorker.ready.then(() => {
        console.log(
          '[ServiceWorker] - This web app is being served cache-first by a service ' +
            'worker. To learn more, visit https://cra.link/PWA'
        );
      });
    } else {
      // Is not localhost. Just register service worker
      console.log('[ServiceWorker] - Registering on Prod server');
      if (REGISTER_SERVICE_WORKER) {
        registerValidSW(swUrl, config);
      }
    }
  }
}

function registerValidSW(swUrl, config) {
  navigator.serviceWorker
    .register(swUrl)
    .then((registration) => {
      console.log('[ServiceWorker] Registering...');
      registration.onupdatefound = () => {
        const installingWorker = registration.installing;
        if (installingWorker == null) {
          return;
        }
        installingWorker.onstatechange = () => {
          if (installingWorker.state === 'installed') {
            if (navigator.serviceWorker.controller) {
              // At this point, the updated precached content has been fetched,
              // but the previous service worker will still serve the older
              // content until all client tabs are closed.
              console.log(
                '[ServiceWorker] - New content is available and will be used when all ' +
                  'tabs for this page are closed. See https://cra.link/PWA.'
              );

              // Execute callback
              if (config && config.onUpdate) {
                config.onUpdate(registration);
              }
            } else {
              // At this point, everything has been precached.
              // It's the perfect time to display a
              // "Content is cached for offline use." message.
              console.log('[ServiceWorker] - Content is cached for offline use.');

              // Execute callback
              if (config && config.onSuccess) {
                config.onSuccess(registration);
              }
            }
          }
        };
      };
    })
    .catch((error) => {
      console.error('[ServiceWorker] - Error during service worker registration:', error);
    });
}

function checkValidServiceWorker(swUrl, config) {
  // Check if the service worker can be found. If it can't reload the page.
  fetch(swUrl, {
    headers: { 'Service-Worker': 'script' },
  })
    .then((response) => {
      // Ensure service worker exists, and that we really are getting a JS file.
      const contentType = response.headers.get('content-type');
      if (response.status === 404 || (contentType != null && contentType.indexOf('javascript') === -1)) {
        // No service worker found. Probably a different app. Reload the page.
        navigator.serviceWorker.ready.then((registration) => {
          registration.unregister().then(() => {
            window.location.reload();
          });
        });
      } else {
        // Service worker found. Proceed as normal.
        registerValidSW(swUrl, config);
      }
    })
    .catch(() => {
      console.log('No internet connection found. App is running in offline mode.');
    });
}

export function unregister() {
  if ('serviceWorker' in navigator) {
    navigator.serviceWorker.ready
      .then((registration) => {
        registration.unregister();
      })
      .catch((error) => {
        console.error(error.message);
      });
  }
}
