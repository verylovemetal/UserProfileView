package user.profile.view.database;

import lombok.Getter;
import lombok.SneakyThrows;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import user.profile.view.ProfilePlugin;
import user.profile.view.database.data.ProfileData;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.UUID;

public class DataStorage {

    @Getter
    private static final DataStorage instance = new DataStorage();
    private final DSLContext dslContext;

    @SneakyThrows
    public DataStorage() {
        File databaseFile = new File(ProfilePlugin.getInstance().getDataFolder(), "database.db");
        String connectionURL = "jdbc:sqlite:" + databaseFile.getPath();

        Connection connection = DriverManager.getConnection(connectionURL);
        this.dslContext = DSL.using(connection, SQLDialect.SQLITE);
    }

    @SneakyThrows
    public ProfileData loadData(String collectionName, UUID key) {
        var record = dslContext.selectFrom(DSL.table(collectionName))
                .where(DSL.field("_id").eq(key.toString()))
                .fetchOne();
        if (record == null) return null;

        return new ProfileData(
                key,
                record.getValue("playerName", String.class),
                record.getValue("description", String.class),
                record.getValue("url", String.class),
                record.getValue("isOnline", Boolean.class)
        );
    }

    public void save(String collectionName, UUID key, ProfileData data) {
        int updated = dslContext.update(DSL.table(collectionName))
                .set(DSL.field("playerName"), data.getPlayerName())
                .set(DSL.field("description"), data.getDescription())
                .set(DSL.field("url"), data.getUrl())
                .set(DSL.field("isOnline"), data.isOnline())
                .set(DSL.field("firstJoinMS"), data.getFirstJoinMS())
                .where(DSL.field("_id").eq(key.toString()))
                .execute();

        if (updated == 0) {
            dslContext.insertInto(DSL.table(collectionName))
                    .columns(DSL.field("_id"),
                            DSL.field("playerName"),
                            DSL.field("description"),
                            DSL.field("url"),
                            DSL.field("isOnline"),
                            DSL.field("firstJoinMS")
                    )
                    .values(
                            key.toString(),
                            data.getPlayerName(),
                            data.getDescription(),
                            data.getUrl(),
                            data.isOnline(),
                            data.getFirstJoinMS()
                    )
                    .execute();
        }
    }

    @SneakyThrows
    public boolean isPresent(String collectionName, UUID key) {
        return dslContext.fetchExists(
                dslContext.selectOne()
                        .from(DSL.table(collectionName))
                        .where(DSL.field("_id").eq(key.toString()))
        );
    }

    @SneakyThrows
    public ProfileData getDataByName(String collectionName, String playerName) {
        var record = dslContext.selectFrom(DSL.table(collectionName))
                .where(DSL.lower(DSL.field("playerName", String.class)).eq(playerName.toLowerCase()))
                .fetchOne();
        if (record == null) return null;

        return new ProfileData(
                UUID.fromString(record.getValue("_id", String.class)),
                record.getValue("playerName", String.class),
                record.getValue("description", String.class),
                record.getValue("url", String.class),
                record.getValue("isOnline", Boolean.class)
        );
    }

    @SneakyThrows
    public void delete(String collectionName, UUID key) {
        dslContext.deleteFrom(DSL.table(collectionName))
                .where(DSL.field("_id").eq(key.toString()))
                .execute();
    }

    @SneakyThrows
    public void initializeDatabase(String collectionName) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + collectionName + " (" +
                "_id varchar(36) NOT NULL," +
                "firstJoinMS INTEGER NOT NULL," +
                "playerName TEXT," +
                "description TEXT," +
                "url TEXT," +
                "isOnline BOOLEAN," +
                "PRIMARY KEY (_id)" +
                ")";

        dslContext.execute(createTableQuery);
    }

    @SneakyThrows
    public void close() {
        dslContext.configuration().connectionProvider().acquire().close();
    }
}
