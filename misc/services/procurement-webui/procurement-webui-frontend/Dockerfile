FROM node:10-alpine as build-step

#RUN npm install yarn

COPY . /

RUN yarn install
RUN yarn build

# Step 2
FROM nginx
COPY --from=build-step build /usr/share/nginx/html
