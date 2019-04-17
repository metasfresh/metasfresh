import config from '../config';

export function getBreadcrumbs(windowId, searchedNode) {
  return cy
    .request('GET', `${config.API_URL}/menu/elementPath?type=window&elementId=${windowId}&inclusive=true`)
    .then(response => {
      expect(response.body).to.have.property('captionBreadcrumb');
      expect(response.body).to.have.property('nodeId');
      let caption = response.body.captionBreadcrumb;
      const nodeId = response.body.nodeId;
      let option = '';

      return cy.request('GET', `${config.API_URL}/menu/node/${nodeId}/breadcrumbMenu`).then(response => {
        const resp = response.body;
        expect(resp.length).to.be.gt(0);

        for (let i = 0; i < resp.length; i += 1) {
          if (resp[i].nodeId === searchedNode) {
            option = resp[i].caption;

            break;
          }
        }

        return new Promise(resolve => {
          resolve({ option, caption });
        });
      });
    });
}
