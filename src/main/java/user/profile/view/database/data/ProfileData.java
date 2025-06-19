package user.profile.view.database.data;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ProfileData {

    @SerializedName(value = "_id")
    private final UUID playerUUID;

    private final long firstJoinMS = System.currentTimeMillis();

    private String playerName;
    private String description = "", url = "";

    private boolean isOnline;
}