# Crux
A bootstrap library for Android that takes care of all the heavy lifting

## API

- **Activity** - SingleStackActivity, DrawerActivity, PagerActivity
- **Fragment** - BaseListFragment, BaseListSearchFragment, BaseListSelectableFragment
- **View** - CruxTextView, CruxEditText, CruxImageView, CruxButton, CruxImageButton
- **Database** - BaseDao, DataDump
- **Utils** - ClassUtils, CollectionUtils, FragmentUtil, IOUtils, MapUtils, PreConditions, ResourceUtils, StringUtils, ToastUtils, ViewUtils

## Libraries Included

- [Fresco](https://github.com/facebook/fresco)
- [ActiveAndroid](https://github.com/pardom/ActiveAndroid)
- [Gson](https://github.com/google/gson)

## Activity

- **SingleStackActivity** : Allows you to create an activity with single stack. Use `pushToStack`, `popFromStack` to push/pop fragment
- **DrawerActivity** : Gives you an option to enable/disable drawer & configure navigation drawer. Override `isNavDrawerEnabled` & `getDrawerMenu`
- **PagerActivity** : Allows you to create an activity with ViewPager. Override `getFragmentInfos` to get started.

## Fragment

- **ListItem** : Defines an item used for constructing list. Implement this for defining any view you wish to use in list.
- **ListSearchableItem** : Defines a `ListItem` with search capability.
- **ListSelectableItem** : Defines a `ListItem` that could be selected.
- **BaseListFragment** : Uses `ListItem` to create list. Override `loadListItemsInBackground`, `onItemClicked` to get started.
- **BaseListSearchFragment** : Allows you to create a list with search capabilities. Use `ListSearchableItem` with the same.
- **BaseListSelectionFragment** : Allows you to create a list with ability to select items. Use `ListSelectableItem` with the same.

## Database

- **BaseDao** : Use this for any CRUD operation in local database. Uses SQLite as database & ActiveAndroid as ORM
- **DataDump** : Use this to dump any data in local file system.

## Sample

The repository includes a sample project built using crux called `HappyShop`. Please refer to it for reference.

## Contributing

Please fork this repository and contribute back using [pull requests](http://github.com/gauravsapiens/crux/pulls). Any contributions would be greatly appreciated

