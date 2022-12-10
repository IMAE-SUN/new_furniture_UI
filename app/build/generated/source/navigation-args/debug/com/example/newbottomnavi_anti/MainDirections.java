package com.example.newbottomnavi_anti;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class MainDirections {
  private MainDirections() {
  }

  @NonNull
  public static ActionNavigationHomeToFurnitureInfoFragment actionNavigationHomeToFurnitureInfoFragment(
      @NonNull String title, @NonNull String price, @NonNull String img) {
    return new ActionNavigationHomeToFurnitureInfoFragment(title, price, img);
  }

  public static class ActionNavigationHomeToFurnitureInfoFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionNavigationHomeToFurnitureInfoFragment(@NonNull String title,
        @NonNull String price, @NonNull String img) {
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
    @SuppressWarnings("unchecked")
    public ActionNavigationHomeToFurnitureInfoFragment setTitle(@NonNull String title) {
      if (title == null) {
        throw new IllegalArgumentException("Argument \"title\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("title", title);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionNavigationHomeToFurnitureInfoFragment setPrice(@NonNull String price) {
      if (price == null) {
        throw new IllegalArgumentException("Argument \"price\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("price", price);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionNavigationHomeToFurnitureInfoFragment setImg(@NonNull String img) {
      if (img == null) {
        throw new IllegalArgumentException("Argument \"img\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("img", img);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
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

    @Override
    public int getActionId() {
      return R.id.action_navigation_home_to_furnitureInfoFragment;
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

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionNavigationHomeToFurnitureInfoFragment that = (ActionNavigationHomeToFurnitureInfoFragment) object;
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
      if (getActionId() != that.getActionId()) {
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
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionNavigationHomeToFurnitureInfoFragment(actionId=" + getActionId() + "){"
          + "title=" + getTitle()
          + ", price=" + getPrice()
          + ", img=" + getImg()
          + "}";
    }
  }
}
