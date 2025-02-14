export const FRONTEND_BASE_URL = process.env.FRONTEND_BASE_URL || 'http://localhost:3001/mobile';
export const BACKEND_BASE_URL = process.env.BACKEND_BASE_URL || 'http://localhost:8282/app/api/v2';

export const SLOW_ACTION_TIMEOUT = 20000; // 20sec
export const VERY_SLOW_ACTION_TIMEOUT = SLOW_ACTION_TIMEOUT * 2;
export const ID_BACK_BUTTON = '#Back-button';

export let page = null;

export const setCurrentPage = (currentPage) => {
    page = currentPage;
}
