FROM --platform=linux/amd64 node:16.11.0 as build
WORKDIR /app
COPY package*.json ./
RUN npm install --legacy-peer-deps
COPY . .
RUN npm run build --prod

FROM --platform=linux/amd64 nginx:1.22.1
COPY --from=build /app/dist/frontend /usr/share/nginx/html
EXPOSE 80
