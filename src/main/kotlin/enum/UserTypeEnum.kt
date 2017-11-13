package enum

enum class UserTypeEnum(val index: Int, val displayName: String) {
    ADMINISTRATOR(0, "Administrator"),
    CLERK(1, "Clerk"),
    CS_WORKER(2, "Custom service worker");

    companion object {
        @JvmStatic
        fun enumOf(index: Int): UserTypeEnum? {
            return values().firstOrNull { it.index == index }
        }

        @JvmStatic
        fun enumOf(displayName: String): UserTypeEnum? {
            return values().firstOrNull { it.displayName == displayName }
        }
    }
}