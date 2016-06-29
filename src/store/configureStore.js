import thunk from 'redux-thunk'
import rootReducer from '../reducers'
import { createStore, applyMiddleware } from 'redux'
import { routerMiddleware } from 'react-router-redux'

export default function configureStore(history){
    const middleware = [thunk, routerMiddleware(history)]
    const store = createStore(
        rootReducer,
        applyMiddleware(...middleware)
    )

    if (module.hot) {
        module.hot.accept('../reducers', () => {
            const nextReducer = rootReducer
            store.replaceReducer(nextReducer)
        })
    }

    return store
}
