import { commonRoutes } from './common';
import { launchersRoutes } from './launchers';
import { workflowRoutes } from './workflow';
import { manufacturingIssueRoutes } from './manufacturing_issue';
import { manufacturingReceiptRoutes } from './manufacturing_receipt';
import { distributionRoutes } from './distribution';
import { pickingRoutes } from './picking';
import { getApplicationRoutes } from '../apps';

export const routesArray = [
  ...commonRoutes,
  ...launchersRoutes,
  ...workflowRoutes,
  ...distributionRoutes,
  ...manufacturingIssueRoutes,
  ...manufacturingReceiptRoutes,
  ...pickingRoutes,
  ...getApplicationRoutes(),
];
