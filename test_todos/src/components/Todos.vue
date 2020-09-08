<template>
	<section class="todoapp">
		<header class="header">
			<h1 class="Todos">Todos</h1>
			<input type="text" class="new-todo" placeholder="add task..." v-model="newTodo" @keyup.enter="addTodo">
		</header>
		<div class="main">
			<ul class="todo-list">
				<li class="todo" v-for="todo in computedList" :key="todo.id" :class="{completed: todo.completed}">
					<div class="view">
            <div class="chkbox">
              <input
                :id="todo.id"
                type="checkbox"
                v-model="todo.completed"
              >
              <span
                class="check"
                @change="save($event, todo)"
                @click="saveHandler($event, todo)"
              ></span>
              <label
                v-show="!todo.editable"
                :for="todo.id"
                @dblclick="todo.editable = !todo.editable"
              >{{ todo.note }}</label>
              <input
                v-show="todo.editable" v-model="todo.note"
                @keypress.enter="save($event, todo)"
                type="text"
                class="todo-editable"
              >
            </div>
            <button class="destroy" @click.prevent="deleteTodo(todo)">&times</button>
					</div>
				</li>
			</ul>
		</div>
		<footer class="footer" v-show="pagesCount > 0">
<!--			<span class="todo-count"><i>{{ remaining }}</i> task is running</span>-->
			<ul class="filters">
        <li><a href="#" :class="{selected: filter === 'all'}" @click.prevent="setFilter('all')">All</a></li>
        <li><a href="#" :class="{selected: filter === 'todo'}" @click.prevent="setFilter('todo')">Todo</a></li>
        <li><a href="#" :class="{selected: filter === 'done'}"  @click.prevent="setFilter('done')">Done</a></li>
      </ul>
      <div class="divider"></div>
      <div class="pagination">
        <button
          class="pagination-item"
          :class="{'pagination-item_selected': i === page}"
          v-for="(i) in pagesCount"
          @click="getNotes(i)"
        >
          {{ i }}
        </button>
      </div>
		</footer>
	</section>
</template>

<script>
  import axios from 'axios';

	export default {
		data () {
			return {
				todos: [],
				newTodo: '',
				filter: 'all',
        page: 1,
        rowPerPage: 5,
        pagesCount: null,
      }
		},

    created() {
		  this.getNotes();
    },

    methods: {
		  setFilter(value) {
        this.filter = value;
        this.getNotes();
      },

		  getNotes(page = this.page) {
		    const params = {
		      page: page - 1,
          rowPerPage: this.rowPerPage,
          filter: this.filter
        };
        axios.get(`/api/all`, {params})
          .then(({data}) => {
            this.todos = [
              ...data.result.map(note => {
                return {
                  ...note,
                  editable: false
                }
              })
            ];
            this.pagesCount = data.pagesCount;
        });
      },

      saveHandler(event, todo) {
		    todo.completed = !todo.completed;
		    this.save(event, todo);
      },

      save(event, todo) {
        todo.editable = false;

		    axios.post('/api/edit', todo).then(() => {
		      this.getNotes();
        })
      },

			addTodo() {
		    const newTodo = {
		      note: this.newTodo,
          completed: false
        };

        axios.put('/api/add', newTodo).then(({ data }) => {
          this.getNotes();
        });

				this.newTodo = ''
			},
			deleteTodo (todo) {
        axios.get(`/api/delete/${todo.id}`).then(() => {
          this.todos = this.todos.filter(i => i !== todo)
        });
			}
		},
		computed: {
			// remaining () {
			// 	return this.todos.filter(todo => !todo.completed).length
			// },
			// filteredTodos () {
			// 	if(this.filter === 'todo') {
			// 		return this.todos.filter(todo => !todo.completed)
			// 	} else if (this.filter === 'done') {
			// 		return this.todos.filter(todo => todo.completed)
			// 	}
			// 	return this.todos
			// },
			computedList() {
    	  return this.todos.filter((item) => {
    	    return item.note && item.note.toLowerCase().indexOf(this.newTodo.toLowerCase()) !== -1
    	  })
    	}
		}
	}

</script>

<style src="./todos.css"></style>
