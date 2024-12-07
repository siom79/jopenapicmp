package jopenapicmp.cmp.compat;

import jopenapicmp.cmp.ApiCompatibilityChange;

import java.util.List;

public interface HasCompatibilityChanges {
	void addCompatibilityChange(ApiCompatibilityChange apiCompatibilityChange);

	List<ApiCompatibilityChange> getApiCompatibilityChanges();
}
