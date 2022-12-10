package com.example.newbottomnavi_anti;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class FurnitureInfoFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private FurnitureInfoFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private FurnitureInfoFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static FurnitureInfoFragmentArgs fromBundle(@NonNull Bundle bundle) {
    FurnitureInfoFragmentArgs __result = new FurnitureInfoFragmentArgs();
    bundle.setClassLoader(FurnitureInfoFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("title")) {
      String title;
      title = bundle.getString("title");
      if (title == null) {
        throw new IllegalArgumentException("Argument \"title\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("title", title);
    } else {
      throw new IllegalArgumentException("Required argument \"title\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("price")) {
      String price;
      price = bundle.getString("price");
      if (price == null) {
        throw new IllegalArgumentException("Argument \"price\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("price", price);
    } else {
      throw new IllegalArgumentException("Required argument \"price\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("img")) {
      String img;
      img = bundle.getString("img");
      if (img == null) {
        throw new IllegalArgumentException("Argument \"img\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("img", img);
    } else {
      throw new IllegalArgumentException("Required argument \"img\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static FurnitureInfoFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    FurnitureInfoFragmentArgs __result = new FurnitureInfoFragmentArgs();
    if (savedStateHandle.contains("title")) {
      String title;
      title = savedStateHandle.get("title");
      if (title == null) {
        throw new IllegalArgumentException("Argument \"title\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("title", title);
    } else {
      throw new IllegalArgumentException("Required argument \"title\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("price")) {
      String price;
      price = savedStateHandle.get("price");
      if (price == null) {
        throw new IllegalArgumentException("Argument \"price\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("price", price);
    } else {
      throw new IllegalArgumentException("Required argument \"price\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("img")) {
      String img;
      img = savedStateHandle.get("img");
      if (img == null) {
        throw new IllegalArgumentException("Argument \"img\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("img", img);
    } else {
      throw new IllegalArgumentException("Required argument \"img\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getTitle() {
    return (String) arguments.get("title");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getPrice() {
    return (String) arguments.get("price");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getImg() {
    return (String) arguments.get("img");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("title")) {
      String title = (String) arguments.get("title");
      __result.putString("title", title);
    }
    if (arguments.containsKey("price")) {
      String price = (String) arguments.get("price");
      __result.putString("price", price);
    }
    if (arguments.containsKey("img")) {
      String img = (String) arguments.get("img");
      __result.putString("img", img);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("title")) {
      String title = (String) arguments.get("title");
      __result.set("title", title);
    }
    if (arguments.containsKey("price")) {
      String price = (String) arguments.get("price");
      __result.set("price", price);
    }
    if (arguments.containsKey("img")) {
      String img = (String) arguments.get("img");
      __result.set("img", img);
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    FurnitureInfoFragmentArgs that = (FurnitureInfoFragmentArgs) object;
    if (arguments.containsKey("title") != that.arguments.containsKey("title")) {
      return false;
    }
    if (getTitle() != null ? !getTitle().equals(that.getTitle()) : that.getTitle() != null) {
      return false;
    }
    if (arguments.containsKey("price") != that.arguments.containsKey("price")) {
      return false;
    }
    if (getPrice() != null ? !getPrice().equals(that.getPrice()) : that.getPrice() != null) {
      return false;
    }
    if (arguments.containsKey("img") != that.arguments.containsKey("img")) {
      return false;
    }
    if (getImg() != null ? !getImg().equals(that.getImg()) : that.getImg() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
    result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
    result = 31 * result + (getImg() != null ? getImg().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "FurnitureInfoFragmentArgs{"
        + "title=" + getTitle()
        + ", price=" + getPrice()
        + ", img=" + getImg()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull FurnitureInfoFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull String title, @NonNull String price, @NonNull String img) {
      if (title == null) {
        throw new IllegalArgumentException("Argument \"title\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("title", title);
      if (price == null) {
        throw new IllegalArgumentException("Argument \"price\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("price", price);
      if (img == null) {
        throw new IllegalArgumentException("Argument \"img\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("img", img);
    }

    @NonNull
    public FurnitureInfoFragmentArgs build() {
      FurnitureInfoFragmentArgs result = new FurnitureInfoFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setTitle(@NonNull String title) {
      if (title == null) {
        throw new IllegalArgumentException("Argument \"title\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("title", title);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setPrice(@NonNull String price) {
      if (price == null) {
        throw new IllegalArgumentException("Argument \"price\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("price", price);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setImg(@NonNull String img) {
      if (img == null) {
        throw new IllegalArgumentException("Argument \"img\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("img", img);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getTitle() {
      return (String) arguments.get("title");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getPrice() {
      return (String) arguments.get("price");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getImg() {
      return (String) arguments.get("img");
    }
  }
}
