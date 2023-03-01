package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;


import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.task.Task;

/**
 * Represents the in-memory model of the repository data.
 */
public class RepositoryModelManager<T extends Relationship<T>> {
    private static final Logger logger = LogsCenter.getLogger(RepositoryModelManager.class);

    private final Repository<T> repo;
    private final FilteredList<T> itemFilteredList;


    /**
     * Initializes a RepositoryModelManager with the given taskBook
     */
    public RepositoryModelManager(ReadOnlyRepository<T> repo) {
        requireNonNull(repo);

        logger.fine("Initializing with repo book: " + repo);

        this.repo = Repository.of(repo);
        itemFilteredList = new FilteredList<>(this.repo.getReadOnlyRepository());
    }

    public void setRepository(Repository<T> repo) {
        this.repo.resetData(repo);
    }

    public ReadOnlyRepository<T> getReadOnlyRepository() {
        return repo;
    }


    /**
     * Returns true if an item with the same identity as {@code item} exists in the repo.
     */
    public boolean hasItem(T item) {
        requireNonNull(item);
        return repo.hasItem(item);
    }

    /**
     * Deletes the given item.
     * {@code target} must exist in the repo.
     */
    public void deleteItem(T target) {
        repo.removeItem(target);
    }

    /**
     * Adds the given item.
     * {@code item} must not already exist in the repo.
     */
    public void addItem(T item) {
        repo.addItem(item);
        showAllItems();
    }

    /**
     * Edits the given item.
     * {@code target} must exist in the repo.
     */

    public void setItem(T target, T editedItem) {
        requireAllNonNull(target, editedItem);

        repo.setItem(target, editedItem);
    }

    //=========== Filtered Person List Accessors =============================================================

    public ObservableList<T> getFilteredItemList() {
        return itemFilteredList;
    }

    /**
     * Updates the filter of the filtered item list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    public void updateFilteredItemList(Predicate<T> predicate) {
        requireNonNull(predicate);
        itemFilteredList.setPredicate(predicate);
    }

    public void showAllItems() {
        updateFilteredItemList(x -> true);
    }


    public ArrayList<Task> returnList() {
         return new ArrayList<>();
    }
    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof RepositoryModelManager<?>)) {
            return false;
        }

        // state check
        RepositoryModelManager<?> other = (RepositoryModelManager<?>) obj;
        return repo.equals(other.repo)
            && itemFilteredList.equals(other.itemFilteredList);
    }

}
