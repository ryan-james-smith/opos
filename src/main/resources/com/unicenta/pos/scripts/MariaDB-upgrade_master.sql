UPDATE applications SET version = $APP_VERSION{} WHERE id = $APP_ID{};
COMMIT;
