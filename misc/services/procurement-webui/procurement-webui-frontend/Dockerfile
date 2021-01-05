FROM node:10-alpine as build-step

LABEL maintainer="info@metasfresh.com"
LABEL runinfo="Run this image with -e PUBLIC_URL=https://yourdomain.org"

COPY . /

RUN yarn install
RUN yarn build

# Step 2
FROM nginx
COPY --from=build-step build /usr/share/nginx/html
