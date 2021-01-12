import axios from 'axios';

const loadMirage = () => import('miragejs');

export function loadMirageInDev() {
  if (process.env.NODE_ENV === 'development') {
    loadMirage().then(({ Server }) => {
      console.log('MIRAGE listening');
      return new Server({
        routes() {
          this.namespace = '/rest/';

          this.get('dailyReport/:date', (schema, request) => {
            const date = request.params.date;
            const products = [
              {
                confirmedByUser: true,
                packingInfo: 'packing info 1',
                productId: '1234',
                productName: 'Broccoli',
                qty: 2,
              },
              {
                confirmedByUser: false,
                packingInfo: 'packing info 2',
                productId: '5678',
                productName: 'Lauch grun',
                qty: 4,
              },
              {
                confirmedByUser: true,
                packingInfo: 'packing info 3',
                productId: '9101',
                productName: 'Apfel',
                qty: 33,
              },
              {
                confirmedByUser: true,
                packingInfo: 'packing info 4',
                productId: '1213',
                productName: 'Kartoffel',
                qty: 99,
              },
              {
                confirmedByUser: true,
                packingInfo: 'packing info 5',
                productId: '1415',
                productName: 'Zweibel',
                qty: 105,
              },
            ];

            const elements = Math.floor(Math.random() * Math.floor(products.length));
            const pickedProducts = products.sort(() => 0.5 - Math.random()).slice(0, elements);

            return {
              date,
              dayCaption: 'Daycaption',
              products: pickedProducts,
            };
          });

          this.get('session', () => {
            return {
              countUnconfirmed: 2,
              date: '01-12-2021',
              dayCaption: 'string',
              email: 'string',
              language: 'de_DE',
              loggedIn: true,
              loginError: 'string',
              week: 'string',
              weekCaption: 'string',
            };
          });
        },
      });
    });
  }
}

export function fetchDailyReport(date: string): Promise<any> {
  return axios.get(`/rest/dailyReport/${date}`);
}
